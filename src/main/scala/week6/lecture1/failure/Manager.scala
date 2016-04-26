package week6.lecture1.failure

import akka.actor.SupervisorStrategy.{Escalate, Restart, Stop}
import akka.actor.{Actor, ActorKilledException, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import akka.actor.Actor.Receive

import scala.concurrent.duration._
import scala.language.postfixOps
import akka.pattern.ask
import akka.util.Timeout
import week6.lecture1.failure.DBActor._


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

class Manager2 extends Actor {
  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _: DBException => Restart //reconnedct to Db
    case _: ActorKilledException => Stop
    case _: ServiceDownException => Escalate
  }

  override def receive: Receive = ???
}

class Manager3 extends Actor {
  var restarts = Map.empty[ActorRef,Int].withDefaultValue(0)

  override val supervisorStrategy: SupervisorStrategy = OneForOneStrategy() { //should be a value, not def. If it were def, this would be re-evaluated on ever failure
    case _: DBException =>
      restarts(sender) match {
        case toomany if toomany > 10 =>
          restarts -= sender; Stop
        case n =>
          restarts = restarts.updated(sender, n + 1); Restart
      }
  }

  override def receive: Receive = ???
}