package week6.lecture1_failure

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
}

object DBActor {
  case class DBException(s: String) extends Exception(s)
}