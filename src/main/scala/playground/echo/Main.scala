package playground.echo

import akka.Done
import akka.actor.{Props, Actor}

/**
  * Created by Hierro on 3/6/16.
  */
class Main extends Actor {
  var count = 0
  println(s"count = $count")

  val actorA = context.actorOf(Props[ActorA], "actorA")
  val actorB = context.actorOf(Props[ActorB], "actorB")

  actorA ! s"Hello ${actorA.path.name}"
  actorB ! s"Hello ${actorB.path.name}"


  override def receive: Receive = {
    case Done =>
      count += 1
      if (count < context.children.size) {
        println(s"count = $count")
      } else {
        println(s"count = $count")
        context.stop(self)
      }
  }
}
