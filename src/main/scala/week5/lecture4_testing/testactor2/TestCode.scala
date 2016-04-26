package week5.lecture4_testing.testactor2

import java.util.concurrent.Executor

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import week5.lecture3_designing.Controller
import week5.lecture4_testing.testactor2.AsyncWebClient.BadStatus

import scala.concurrent.Future

/**
  * Created by Hierro on 4/25/16.
  */
object GetterSpec {

  val firstLink = "http://agaro1121.github.io/1"

  val bodies = Map(
    firstLink ->
      """<html>
              <head><title>Page 1</title></head>
              <body>
                  <h1>A Link</h1>
                  <a href="http://www.google.com/1">click here</a>
              </body>
        </html>"""
  )

  val links = Map(firstLink -> Seq("http://www.google.com/1"))

  object FakeWebClient extends WebClient {
    override def get(url: String)(implicit exec: Executor): Future[String] =
      bodies get url match {
        case None => Future.failed(BadStatus(404))
        case Some(body) => Future.successful(body)
      }
  }

  def fakeGetter(url: String, depth: Int): Props =
    Props(new Getter(url, depth) {
      //needed for future and pipeTo //this is an executor and execution context
      override def client: WebClient = FakeWebClient
    })

  /**
    * Need this because the Getter sends messages to the parent.
    * In this test, the System creates the getter and that is the Guardian.
    * We cannot monitor the guardian.
    * this will allow us to monitor messages.
    * *
    * The probe is the actor the messages are forwarded to
    * So whenever it receives a message, it will forward it with the original address
    * In this case, the original sender will be the Getter
    */
  class StepParent(child: Props, probe: ActorRef) extends Actor {
    context.actorOf(child, "child")

    override def receive: Receive = {
      case msg => probe.tell(msg, sender)
    }
  }

  /**
    * Slight variation to the above implementation to the above
    *
    * Mediator between Parent and Child
    *
    * This might be bad because if it's actorRef is exposed
    * by either parent or child to outside Actors,
    * then we have no way of knowing where the messages were
    * originally supposed to go
    **/
  class FosterParent(child1: Props, probe: ActorRef) extends Actor {
    val child = context.actorOf(child1, "child")

    override def receive: Receive = {
      case msg if sender == context.parent =>
        probe forward msg
        child forward msg

      case msg =>
        probe forward msg
        context.parent forward msg
    }


  }

}

class GetterSpec extends TestKit(ActorSystem("GetterSpec")) with WordSpecLike with BeforeAndAfterAll
  with ImplicitSender {

  import GetterSpec._

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "A Getter" must {

    "return the right body" in {
      val getter = system.actorOf(Props(new StepParent(fakeGetter(firstLink, 2), testActor)), "rightBody")
      for (link <- links(firstLink))
        expectMsg(Controller.Check(link, 2))
      expectMsg(Getter.Done)
    }

    "properly finish in case of errors" in {
      val getter = system.actorOf(Props(new StepParent(fakeGetter("unknown", 2), testActor)), "wrongLink")
      expectMsg(Getter.Done)
    }
  }

}