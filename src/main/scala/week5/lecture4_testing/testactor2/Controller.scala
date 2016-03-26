package week5.lecture4_testing.testactor2

import akka.actor.{ActorSystem, Props, Actor}
import akka.pattern.ask
import akka.testkit.TestKit
import akka.util.Timeout
import org.scalatest.{WordSpecLike, BeforeAndAfterAll}
import scala.concurrent.duration._
/**
  * Created by anthonygaro on 3/25/16.
  */
class Controller extends Actor{

  val getter = context.actorOf(Props[Getter], "getter")
  implicit val timeout = Timeout(1,SECONDS)
  implicit val exec = context.dispatcher

  override def receive: Receive = {
    case url:String =>
      val resp = getter ? url
      resp.map(println(_))
  }
}

class ControllerSpec extends TestKit(ActorSystem("ControllerTest"))
with WordSpecLike with BeforeAndAfterAll
{




}