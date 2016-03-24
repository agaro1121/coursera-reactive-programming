package playground.greeting1

import akka.actor.Actor
import akka.pattern.ask

/**
  * Created by Hierro on 3/6/16.
  */
class ActorA extends Actor {
  override def receive: Receive = {
    case x =>
      println(s"Message from ${sender.path.name}. Contents are: ${x}")
      sender ! s"I have received your message ${sender.path.name}"
  }
}
