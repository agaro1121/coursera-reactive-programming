package week6.deatch.watch.receptionist

import akka.actor.{Actor, ActorRef}
import akka.pattern.pipe

class Cache extends Actor {

  import Cache._

  var cache = Map.empty[String, String]
  implicit val exec = context.dispatcher

  def receive = {
    case Get(url) =>
      if (cache contains url) sender ! cache(url)
      else {
        val client = sender //need this because below map function happens in the future and might not get correct/current value
        WebClient get url map(Result(client, url, _)) pipeTo self
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
