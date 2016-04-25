package week5.lecture1_actor_model

import akka.actor.Actor

/**
  * Created by hierro on 3/2/16.
  */
class Counter2 extends Actor {
  def counter(n: Int): Receive ={
    case "incr" => context.become(counter(n + 1)) //become function permanently changes behavior to next count n++
    case "get" => sender ! n
  }

  def receive = counter(0)
}
