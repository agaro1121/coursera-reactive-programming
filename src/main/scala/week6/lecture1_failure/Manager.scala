package week6.lecture1_failure

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Props, OneForOneStrategy, SupervisorStrategy, Actor}
import akka.actor.Actor.Receive
import week6.lecture1_failure.DBActor.DBException
import scala.concurrent.duration._
import scala.language.postfixOps
import akka.pattern.ask
import akka.util.Timeout

/**
  * Created by hierro on 3/29/16.
  */
class Manager extends Actor {

  implicit val timeout = Timeout(3, SECONDS)
  implicit val exec = context.dispatcher

  //Tweaking the number of retries to anything less than 5 will show the limitations working
  override val supervisorStrategy: SupervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, 1 minute) {
    case _:DBException => Restart
  }

  val dbActor = context.actorOf(Props[DBActor],"dbActor")

  //should show approximately 5-6 failures.
  //Should fail more but some messages get abandoned during restart so they don't increase the count before it fails
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))
  (dbActor ? "get") onComplete(println(_))


  override def receive: Receive = {
    case s:String => println("Hello World")
  }
}
