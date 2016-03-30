package week5.lecture4_testing.testactor1

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by Hierro on 3/23/16.
  */
object TestBiPolar extends App {
  override def main(args: Array[String]) {
    val t = new TestKit(ActorSystem("TestSys")) with ImplicitSender {
      val toggle = system.actorOf(Props[BiPolar])
      toggle ! "How are you?"
      expectMsg("happy")
      toggle ! "How are you?"
      expectMsg("sad")
      toggle ! "unknown"
      expectNoMsg(1 second)
      system.terminate()
    }

  }
}
