package week5.lecture4_testing.testactor2

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.duration._
import scala.language.postfixOps
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import week5.lecture3_designing.Cache.Get

/**
  * Created by Hierro on 4/25/16.
  */
object ReceptionistSpec {

  class FakeController extends Actor {

    import context.dispatcher

    override def receive: Receive = {
      case Controller.Check(url, depth) =>
        context.system.scheduler.scheduleOnce(1 second, sender, Controller.Result(Set(url)))
    }
  }


  def fakeReceptionst: Props =
    Props(new Receptionist {
      override def controllerProps: Props = Props[FakeController]
    })

}

class ReceptionistSpec
  extends TestKit(ActorSystem("ReceptionistSpec"))
    with WordSpecLike
    with BeforeAndAfterAll
    with ImplicitSender {
  import ReceptionistSpec._
  import Receptionist._

  "A Receptionist" must {

    "reply with a result" in {
      val receptionist = system.actorOf(fakeReceptionst,"sendResult")
      receptionist ! Get("myURL")
      expectMsg(Result("myURL",Set("myURL")))
    }

    "reject request floor" in {
      val receptionist = system.actorOf(fakeReceptionst, "rejectFlood")
      for(i <- 1 to 5) receptionist ! Get(s"myURL$i")
      expectMsg(Failed("myURL5"))
      for(i <- 1 to 4) expectMsg(Result(s"myURL$i",Set(s"myURL$i")))
    }
  }



}