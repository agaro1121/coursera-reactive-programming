package week7.eventual.consistency

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.ActorDSL._

/**
  * Created by Hierro on 5/1/16.
  */
case class Update(x: Int)
case object Get
case class Result(x: Int)
case class Sync(x: Int, timestamp: Long)
case object Hello

class DistributedStore extends Actor{
  var peers: List[ActorRef] = Nil
  var field = 0
  var lastUpdate = System.currentTimeMillis()

  override def receive: Receive = {
    case Update(x) =>
      field = x
      lastUpdate = System.currentTimeMillis()
      peers foreach(_ ! Sync(field,lastUpdate))

    case Get => sender ! Result(field)

    case Sync(x,timestamp) if timestamp > lastUpdate =>
      field = x
      lastUpdate = timestamp

    case Hello =>
      peers ::= sender
      sender ! Sync(field,lastUpdate)

  }
}

object Main extends App {
  implicit val system = ActorSystem("distributed")
  implicit val sender = actor(new Act { become { case msg => println(msg)}})

  val a = system.actorOf(Props[DistributedStore])
  val b = system.actorOf(Props[DistributedStore])

  a ! Get
  b ! Get
  a ! Update(42)
  a ! Get
  b ! Get
  a tell(Hello,b)
  b ! Get
  b ! Update(43)
  b ! Get
  a ! Get
  b tell(Hello,a)//required because introduction needs to be done both ways for syncing
  a ! Get
}