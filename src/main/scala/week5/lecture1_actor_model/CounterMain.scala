package week5.lecture1_actor_model

import akka.actor.{Actor, Props}

/**
  * Created by hierro on 3/2/16.
  */
class CounterMain extends Actor{
  val counter = context.actorOf(Props[Counter],"counter")
  val counter2 = context.actorOf(Props[Counter2],"counter2")

  counter ! "incr"
  counter ! "incr"
  counter ! "incr"
  counter ! "get"

  counter2 ! "incr"
  counter2 ! "incr"
  counter2 ! "incr"
  counter2 ! "get"


  override def receive: Receive = {

    case count: Int =>
      println(s"count was $count")
    if(sender == counter2) context.stop(self)
  }
}
