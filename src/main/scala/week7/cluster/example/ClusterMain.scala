package week7.cluster.example

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorIdentity, ActorLogging, ActorPath, ActorRef, Address, Deploy, Identify, Props, ReceiveTimeout, RootActorPath, SupervisorStrategy, Terminated}
import akka.cluster.ClusterEvent.{MemberRemoved, MemberUp}
import akka.cluster.{Cluster, ClusterEvent}
import akka.remote.RemoteScope
import week5.lecture4_testing.testactor2.{AsyncWebClient, Controller}
import week6.deatch.watch.receptionist.Cache.Get

import scala.concurrent.duration.FiniteDuration
import scala.util.Random

/**
  * Created by anthonygaro on 4/27/16.
  */

/**
  * This will start a single-node cluster on port 2552 using TCP
  **/
class ClusterMain extends Actor {

  import ClusterReceptionistst._
  import scala.concurrent.duration._

  val cluster = Cluster(context.system)
  cluster.subscribe(self, classOf[ClusterEvent.MemberUp])
  cluster.subscribe(self, classOf[ClusterEvent.MemberRemoved])
  cluster.join(cluster.selfAddress)

  val receptionist = context.actorOf(Props[ClusterReceptionist], "receptionist")
  context.watch(receptionist)

  //sign death pact

  def getLater(d: FiniteDuration, url: String): Unit = {
    import context.dispatcher
    context.system.scheduler.scheduleOnce(d, receptionist, Get(url))
  }

  getLater(Duration.Zero, "http://www.google.com")

  override def receive: Receive = {
    case ClusterEvent.MemberUp(member) =>
      if (member.address != cluster.selfAddress) {
        getLater(1 seconds, "http://www.google.com")
        getLater(2 seconds, "http://www.google.com/0")
        getLater(2 seconds, "http://www.google.com/1")
        getLater(3 seconds, "http://www.google.com/2")
        getLater(4 seconds, "http://www.google.com/3")
        context.setReceiveTimeout(3 seconds)
      }

    case ClusterReceptionistst.Result(url, set) =>
      println(set.toVector.sorted.mkString(s"Results for '$url':\n", "\n", "\n"))
    case Failed(url, reason) =>
      println(s"Failed to fetch '$url': $reason\n")
    case ReceiveTimeout =>
      cluster.leave(cluster.selfAddress)
    case ClusterEvent.MemberRemoved(m, _) =>
      context.stop(self)
  }
}

/**
  * This needs configuration akka.remote.netty.tcp.port = 0
  * Setting it to Zero = random
  *
  *
  * Don't need to know at which port the ClusterWorker lives because it will just join the main one
  **/
class ClusterWorker extends Actor with ActorLogging {
  val cluster = Cluster(context.system)
  cluster.subscribe(self, classOf[ClusterEvent.MemberRemoved])
  cluster.subscribe(self, classOf[ClusterEvent.MemberUp])

  val main: Address = cluster.selfAddress.copy(port = Some(2552)) //address of cluster main can be derived from the actor's self address with port 2552
  cluster.join(main)

  //worker joins main cluster

  /*  override def receive: Receive = {
      case ClusterEvent.MemberRemoved(m, _) =>
        if (m.address == main) context.stop(self) //when main program shuts down, this also stops
    }*/
  override def receive: Receive = {
    //modified for deathwatch
    case ClusterEvent.MemberRemoved(m, _) =>
      if (m.address == main) {
        val path = RootActorPath(main) / "user" / "app" / "receptionist"
        context.actorSelection(path) ! Identify("42")
      }
    case ClusterEvent.MemberUp(member) =>
          if (member.address == main) {
            val path = RootActorPath(main) / "user" / "app" / "receptionist"
            context.actorSelection(path) ! Identify("42")
          }

    case ActorIdentity("42", None) => context.stop(self)
    case ActorIdentity("42", Some(ref)) =>
      log.info("reference is at {}", ref)
      context.watch(ref)
    case Terminated(_) => context.stop(self)
      cluster.system.terminate()


  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    AsyncWebClient.shutdown()
  }
}

/**
  * Going to modify this Receptionist to spawn actors remotely
  *
  * 1. Receptionist needs to know everything (MemberUp, MemberRemoved)
  **/
class ClusterReceptionist extends Actor {

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
      val addresses = current.members.toVector map (x => x.address)
      val notMe = addresses filter (_ != cluster.selfAddress)
      if (notMe.nonEmpty) context.become(active(notMe)) //if there's anyone in the cluster besides the Receptionist, become active

    case MemberUp(member) if member.address != cluster.selfAddress =>
      context.become(active(Vector(member.address))) //if new member comes in, change to active state and wih that new member in the list

    case Get(url) => sender ! Failed(url, "no nodes available") //behavior is awaitingMembers so there are no resources to perform task

  }

  /**
    * This behavior will also have to monitor the cluster for adding and removing members
    **/
  def active(addresses: Vector[Address]): Receive = {

    case MemberUp(member) if member.address != cluster.selfAddress => //someone else besides me
      context.become(active(addresses :+ member.address))

    case MemberRemoved(member, _) =>
      val next = addresses filterNot (_ == member.address)
      if (next.isEmpty) context.become(awaitingMembers)
      else context.become(active(next))

    case Get(url) if context.children.size < addresses.size => //if running context is smaller than the known cluster addresses
      val client = sender
      val address = pick(addresses) //pick a random address
      context.actorOf(Props(new Customer(client, url, address))) //client, url that is supposed to be retrieved, cluster address where work will be performed

    case Get(url) =>
      sender ! Failed(url, "too  many parallel queries")

  }

  def pick(addresses: Vector[Address]): Address = {
    Random.shuffle(addresses.toList).head
  }
}

object ClusterReceptionistst {

  case class Failed(url: String, msg: String)

  case class Result(url: String, links: Set[String])

}

/**
  * Responsible for making sure the given URL is retrieved
  *
  * Work is performed at a remote node
  **/
class Customer(client: ActorRef, url: String, node: Address) extends Actor {

  import scala.concurrent.duration._

  //all messages sent by this customer will appear to be sent by the parent instead
  implicit val parent = context.parent //used as sender reference for messages - takes precedence over implicit value found in Actor trait because it is in current scope


  override def supervisorStrategy: SupervisorStrategy = SupervisorStrategy.stoppingStrategy

  val props = Props[Controller].withDeploy((Deploy(scope = RemoteScope(node))))
  //Deploy is a Description of how to deploy the actor
  val controller = context.actorOf(props, "controller")
  context.watch(controller)

  context.setReceiveTimeout(5 seconds)
  controller ! Controller.Check(url, 2)

  override def receive = ({
    case ReceiveTimeout =>
      context.unwatch(controller)
      client ! ClusterReceptionistst.Failed(url, "controller timed out")

    case Terminated(_) =>
      client ! ClusterReceptionistst.Failed(url, "controller died")

    case Controller.Result(links) =>
      context.unwatch(controller)
      client ! ClusterReceptionistst.Result(url, links)
  }: Receive) //need to explicitly put the type here
    .andThen(_ => context.stop(self))
}