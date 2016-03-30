package playground.greeting1

import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive
import akka.util.Timeout

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask
import scala.concurrent.duration._

/**
  * Created by Hierro on 3/6/16.
  */
class Main extends Actor {

  val actorA = context.actorOf(Props[ActorA], "actorA")
  val actorB = context.actorOf(Props[ActorA], "actorB")
  implicit val timeout = Timeout(5 seconds)

  val resp = actorB ? "Wassup"
  resp onSuccess{case x => println(x)}


  override def receive: Receive = {
    case x: Any => println(x)
  }


}
