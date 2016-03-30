package week6.lecture2.eventstream

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging.LogEvent

/**
  * Created by anthonygaro on 3/30/16.
  */
class Listener extends Actor {
  context.system.eventStream.subscribe(self,classOf[LogEvent])

  override def receive: Receive = {
    case e: LogEvent => println(e.toString)
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    context.system.eventStream.unsubscribe(self)
  }
}
