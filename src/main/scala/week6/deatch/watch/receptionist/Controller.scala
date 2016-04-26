package week6.deatch.watch.receptionist

import akka.actor._
import akka.util.Timeout

import scala.concurrent.duration._

/**
  * Created by Hierro on 3/5/16.
  */
class Controller extends Actor with ActorLogging {

  import Controller._

  var cache = Set.empty[String]
  var children = Set.empty[ActorRef]
  context.setReceiveTimeout(10 seconds)

  /**
    * Instead of setting a Timeout.
    * You can use the scheduler here:
    *
    * context.system.scheduler.scheduleOnce(10 seconds) {
    *   children foreach (_ ! Getter.Abort)
    * }
    *
    * Problems: Not Thread Safe !!!
    * The above code runs outside the context of the actor.
    * It is run by the scheduler. This does not guarantee it will run concurrently with the actor.
    *
    * The code inside the block has access to the (mutable) variable `children`
    * and it will try to modify it after the designated time. This is not synchronized!!!
    */



  override def receive: Receive = {
    case Check(url, depth) =>
      log.debug("{} checking {}", depth, url) //'{}' place holder. Basically a string format
      if (!cache(url) && depth > 0)
        children += context.actorOf(Props(new Getter(url, depth - 1)))
      cache += url

    case Getter.Done =>
      children -= sender
      if (children.isEmpty) context.parent ! Result(cache)

    case Timeout => children foreach (_ ! Getter.Abort)
  }

}

object Controller {

  case class Check(link: String, depth: Int)

  case class Result(cache: Set[String])

}