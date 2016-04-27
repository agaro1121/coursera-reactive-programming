package week7.greeter

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by anthonygaro on 4/27/16.
  */
class Greeter extends Actor {
  override def receive: Receive = {
    case msg => println(msg)
  }
}
