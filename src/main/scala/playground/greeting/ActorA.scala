package playground.greeting

import akka.actor.Actor

/**
  * Created by Hierro on 3/6/16.
  */
class ActorA extends Actor{
  import ActorA._

  override def receive: Receive = {
    case Greeting(nm) =>
      println(s"Greetings from ${sender.path.name}")
      sender ! s"hello $nm"
  }
}

object ActorA {
  case class Greeting(s: String)
}