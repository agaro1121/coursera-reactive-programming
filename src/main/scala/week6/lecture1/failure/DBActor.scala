package week6.lecture1.failure

import akka.actor.{ActorLogging, Actor}
import akka.actor.Actor.Receive

/**
  * Created by hierro on 3/29/16.
  */
class DBActor extends Actor with ActorLogging {
  import DBActor._
  var count = 0

  override def receive: Receive = {
    case "get"  => if(count < 3) {count += 1; sender ! count} else throw new DBException("DB Exception")

  }

  @scala.throws[Exception](classOf[Exception])
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"Restarting due to throwable = ${reason.toString} and message = ${message foreach(x => println(x))}")
  }

}

object DBActor {
  case class DBException(s: String) extends Exception(s)
}