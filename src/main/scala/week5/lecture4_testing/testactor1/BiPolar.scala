package week5.lecture4_testing.testactor1

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by Hierro on 3/23/16.
  */
class BiPolar extends Actor {

  def happy: Receive = {
    case "How are you?" =>
      println("happy")
      sender ! "happy"
      context become sad
  }

  def sad: Receive = {
    case "How are you?" =>
      println("sad")
      sender ! "sad"
      context become happy
  }

  override def receive: Receive = happy
}
