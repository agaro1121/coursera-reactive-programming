package week5.lecture4_testing.testactor2

import akka.actor.{Actor, ActorRef, Props}
import week5.lecture3_designing.Cache.Get

class Receptionist extends Actor {
  import Receptionist._

  def receive = waiting

  def controllerProps = Props[Controller]

  var reqNo = 0

  def runNext(queue: Vector[Job]): Receive = { //pick the next job and run it!
    reqNo += 1
    if (queue.isEmpty) waiting
    else {
      val controller = context.actorOf(controllerProps, s"c$reqNo" )
      controller ! Controller.Check(queue.head.url, 2) //depth
      running(queue)
    }
  }

  val waiting: Receive = {
    case Get(url) => context.become(runNext(Vector(Job(sender, url))))
  }

  def running(queue: Vector[Job]): Receive = {
    case Controller.Result(links) =>
      val job = queue.head
      job.client ! Result(job.url, links)
      context.stop(sender)
      context.become(runNext(queue.tail))

    case Get(url) =>
      context.become(enqueueJob(queue, Job(sender, url)))
  }

  def enqueueJob(queue: Vector[Job], job: Job): Receive = {
    if (queue.size > 3) {
      sender ! Failed(job.url)
      running(queue)
    } else running(queue :+ job)
  }
}

object Receptionist {

  case class Job(client: ActorRef, url: String)
  case class Result(url: String, links: Set[String])
  case class Failed(url: String)

}