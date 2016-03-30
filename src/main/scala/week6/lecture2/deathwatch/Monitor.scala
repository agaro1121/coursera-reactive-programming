package week6.lecture2.deathwatch

import akka.actor.{Actor, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
/**
  * Created by anthonygaro on 3/30/16.
  */
class Monitor extends Actor {

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 2) {
    case _: Exception => SupervisorStrategy.Restart
  }

  implicit val timeout = Timeout(3, SECONDS)
  implicit val exec = context.dispatcher

  val dbActor = context.actorOf(Props[DBActor],"dbActor")
  context.watch(dbActor)

  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))
  (dbActor ? "get") onComplete(x => println("response = " + x))

  override def receive: Receive = {
    case s:String => println("Monitor receives message = "+s)
    case Terminated(_) => println(s"Actor ${sender.path.name} is terminated")
  }
}
