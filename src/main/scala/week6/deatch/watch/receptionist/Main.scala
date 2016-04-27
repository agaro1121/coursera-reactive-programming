package week6.deatch.watch.receptionist

import akka.actor.{Actor, Props, ReceiveTimeout}
import week6.deatch.watch.receptionist.Cache.Get

import scala.concurrent.duration._

/**
  * Created by Hierro on 3/5/16.
  */
class Main extends Actor {
  import Receptionist._

  val receptionist = context.actorOf(Props[Receptionist], "receptionist")
  context.watch(receptionist) //sign death pact

  receptionist ! Get("http://www.google.com/1")
  receptionist ! Get("http://www.google.com/2")
  receptionist ! Get("http://www.google.com/3")
  receptionist ! Get("http://www.google.com/4")
  receptionist ! Get("http://www.google.com/5")

  context.setReceiveTimeout(10 seconds)

  override def receive: Receive = {
    case Result(url,set) =>
      println(set.toVector.sorted.mkString(s"Results for '$url': \n", "\n", "\n"))
    case Failed(url) =>
      println(s"Failed to fetch '$url' \n")
    case ReceiveTimeout =>
      println("timeout")
      context.stop(self)
  }

  @throws[Exception](classOf[Exception])
  override def postStop(): Unit = WebClient.shutdown()
}

object Main extends App
