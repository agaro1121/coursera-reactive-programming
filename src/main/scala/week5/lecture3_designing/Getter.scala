package week5.lecture3_designing

import akka.actor.{Actor, Status}
import akka.pattern.pipe

import scala.concurrent.Future

/**
  * Created by Hierro on 3/5/16.
  */
class Getter(url: String, depth: Int) extends Actor {

  import WebClient._
  import Getter._

  implicit val exec = context.dispatcher //needed for future and pipeTo //this is an executor and execution context

  val future: Future[String] = WebClient get url pipeTo self


  override def receive: Receive = {
    case body: String =>
      for (link <- findLinks(body, url))
        context.parent ! Controller.Check(link, depth)
        stop()

    case _: Status.Failure => stop()

    case Abort => stop()
  }


  def stop(): Unit = {
    context.parent ! Done
    context.stop(self)
  }

}

object Getter {

  case object Abort

  case object Done

  case object Failed

}