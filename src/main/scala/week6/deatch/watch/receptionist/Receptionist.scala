package week6.deatch.watch.receptionist

import akka.actor.{Actor, ActorRef, Props, SupervisorStrategy, Terminated}
import week6.deatch.watch.receptionist.Cache.Get

class Receptionist extends Actor {
  import Receptionist._


  override def supervisorStrategy: SupervisorStrategy = SupervisorStrategy.stoppingStrategy

  def receive = waiting

  var reqNo = 0

  def runNext(queue: Vector[Job]): Receive = { //pick the next job and run it!
    reqNo += 1
    if(reqNo == 3) context.stop(self) /**some hacking to make it fail */
    if (queue.isEmpty) waiting
    else {
      val controller = context.actorOf(Props[Controller], s"c$reqNo" )
      context.watch(controller)
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
      context.stop(context.unwatch(sender))
      context.become(runNext(queue.tail))

    case Terminated(_) =>
      val job = queue.head
      job.client ! Failed(job.url)
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