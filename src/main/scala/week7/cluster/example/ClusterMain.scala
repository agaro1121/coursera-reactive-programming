package week7.cluster.example

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, Address, Deploy, Props, SupervisorStrategy}
import akka.cluster.ClusterEvent.{MemberRemoved, MemberUp}
import akka.cluster.{Cluster, ClusterEvent}
import akka.remote.RemoteScope

/**
  * Created by anthonygaro on 4/27/16.
  */
object ClusterMain {

}

/**
  * This will start a single-node cluster on port 2552 using TCP
  **/
class ClusterMain extends Actor {
  val cluster = Cluster(context.system)
  cluster.subscribe(self, classOf[ClusterEvent.MemberUp])
  cluster.join(cluster.selfAddress)

  override def receive: Receive = {
    case ClusterEvent.MemberUp(member) =>
      if (member.address != cluster.selfAddress) {
        //someone else joined
      }
  }
}

/**
  * This needs configuration akka.remote.netty.tcp.port = 0
  * Setting it to Zero = random
  *
  *
  * Don't need to know at which port the ClusterWorker lives because it will just join the main one
  * */
class ClusterWorker extends Actor {
  val cluster = Cluster(context.system)
  cluster.subscribe(self, classOf[ClusterEvent.MemberRemoved])

  val main: Address = cluster.selfAddress.copy(port = Some(2552)) //address of cluster main can be derived from the actor's self address with port 2552
  cluster.join(main) //worker joins main cluster

  override def receive: Receive = {
    case ClusterEvent.MemberRemoved(m, _) =>
      if (m.address == main) context.stop(self) //when main program shuts down, this also stops
  }
}

/**
  * Going to modify this Receptionist to spawn actors remotely
  *
  * 1. Receptionist needs to know everything (MemberUp, MemberRemoved)
  * */
class ClusterReceptionist extends Actor {
  import Cache._
  import ClusterReceptionistst._

  val cluster = Cluster(context.system)
  cluster.subscribe(self, classOf[MemberUp])
  cluster.subscribe(self, classOf[MemberRemoved])

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  override def receive: Receive = awaitingMembers

  val awaitingMembers: Receive = {
    case current: ClusterEvent.CurrentClusterState => //in response to cluster.subscribe actor will always receive first current cluster states
      val addresses = current.members.toVector map(x => x.address)
      val notMe = addresses filter (_ != cluster.selfAddress)
      if(notMe.nonEmpty) context.become(active(notMe)) //if there's anyone in the cluster besides the Receptionist, become active

    case MemberUp(member) if member.address != cluster.selfAddress =>
      context.become(active(Vector(member.address))) //if new member comes in, change to active state and wih that new member in the list

    case Get(url) => sender ! Failed(url,"no nodes available") //behavior is awaitingMembers so there are no resources to perform task

  }

  /**
    * This behavior will also have to monitor the cluster for adding and removing members
    * */
  def active(addresses: Vector[Address]): Receive = {

     case MemberUp(member) if member.address != cluster.selfAddress => //someone else besides me
      context.become(active(addresses :+ member.address))

    case MemberRemoved(member,_) =>
      val next = addresses filterNot(_ == member.address)
      if(next.isEmpty) context.become(awaitingMembers)
      else context.become(active(next))

     case Get(url) if context.children.size < addresses.size => //if running context is smaller than the known cluster addresses
      val client = sender
      val address = pick(addresses) //pick a random address
      context.actorOf(Props(new Customer(client, url, address))) //client, url that is supposed to be retrieved, cluster address where work will be performed

     case Get(url) =>
      sender ! Failed(url,"too  many parallel queries")

  }
}

object ClusterReceptionistst {

  case class Failed(url: String, msg: String)

}

/**
  * Responsible for making sure the given URL is retrieved
  *
  * Work is performed at a remote node
  * */
class Customer(client: ActorRef, url: String, node: Address) extends Actor {
  import scala.concurrent.duration._

  //all messages sent by this customer will appear to be sent by the parent instead
  implicit val parent = context.parent //used as sender reference for messages - takes precedence over implicit value found in Actor trait because it is in current scope


  override def supervisorStrategy: SupervisorStrategy = SupervisorStrategy.stoppingStrategy

  val props = Props[Controller].withDeploy((Deploy(scope = RemoteScope(node)))) //Deploy is a Description of how to deploy the actor
  val controller = context.actorOf(props,"controller")
  context.watch(controller)

  context.setReceiveTimeout(5 seconds)
  controller ! Controller.Check(url,2)

  override def receive: Receive = ???
}