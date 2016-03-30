package playground.greeting

import akka.actor.{Props, Actor}
import akka.util.Timeout
import playground.greeting.ActorA.Greeting
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Hierro on 3/6/16.
  */
class ActorB extends Actor {
  import ActorB._

  val actorA = context.actorOf(Props[ActorA],"actorA")
  implicit val timeout = Timeout(5 seconds)

  val resp = actorA ? ActorA.Greeting("ActorB")
  resp onSuccess { case s: Any =>
    println(s)
    context.stop(self)
  }


  override def receive: Receive = {
    case Response(resp) => println(resp)
  }
}

object ActorB {
  case class Response(s: Any)
}
