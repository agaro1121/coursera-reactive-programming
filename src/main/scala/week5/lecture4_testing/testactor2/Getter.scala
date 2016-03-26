package week5.lecture4_testing.testactor2

import akka.actor.Actor

/**
  * Created by anthonygaro on 3/25/16.
  */
class Getter extends Actor{

  implicit val exec = context.dispatcher
  val client = WebClient

  override def receive: Receive = {
    case url:String => sender ! WebClient.get(url)
  }
}
