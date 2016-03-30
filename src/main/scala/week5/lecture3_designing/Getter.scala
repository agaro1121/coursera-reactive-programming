package week5.lecture3_designing

import akka.Done
import akka.actor.{Identify, Status, Actor}
import akka.actor.Actor.Receive

/**
  * Created by Hierro on 3/5/16.
  */
class Getter(url: String, depth: Int) extends Actor{

  implicit val exec = context.dispatcher

  val future = WebClient get url pipeTo self


  override def receive: Receive = {
    case body: String =>
      for (link <- WebClient.findLinks(body,url))
        context.parent ! Controller.Check(link,depth)
      stop()
    case _:Status.Failure => stop()
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