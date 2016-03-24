package week5.lecture3_designing

import akka.actor.{ActorRef, Actor}
import week5.lecture3_designing.WebClient

import akka.pattern.PipeableFuture


class Cache extends Actor {

  import Cache._

  var cache = Map.empty[String, String]
  implicit val exec = context.dispatcher

  def receive = {
    case Get(url) =>
      if (cache contains url) sender ! cache(url)
      else {
        val client = sender
        val f = (WebClient get url).future.map(Result(client, url, _))
        val piper = new PipeableFuture(f)
        piper pipeTo self
      }
    case Result(client, url, body) =>
      cache += url -> body
      client ! body
  }
}

object Cache {

  case class Get(url: String)

  case class Result(client: ActorRef, url: String, body: String)

}
