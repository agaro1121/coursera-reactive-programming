package playground.echo

import akka.Done
import akka.actor.Actor

/**
  * Created by Hierro on 3/6/16.
  */
class ActorA extends Actor {
  override def receive: Receive = {
    case x => {
      println(x)
      context.parent ! Done
    }
  }
}