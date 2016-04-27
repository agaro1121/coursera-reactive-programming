package week7.resolver

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorIdentity, ActorPath, ActorRef, Identify}

/**
  * Created by anthonygaro on 4/27/16.
  */
class Resolver extends Actor{
  import Resolver._

  override def receive: Receive = {
    case Resolve(path) =>
      context.actorSelection(path) ! Identify((path,sender))

    case ActorIdentity((path,client), Some(ref)) =>
      client ! Resolved(path,ref)

    case ActorIdentity((path,client), None) =>
      client ! NotResolved(path)
  }
}

object Resolver {

  case class Resolve(path: ActorPath)

  case class Resolved(path: ActorPath, ref: ActorRef)

  case class NotResolved(path: ActorPath)

}