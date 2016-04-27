package week6.event.example.one

import akka.actor.ActorPath
import akka.persistence.{AtLeastOnceDelivery, PersistentActor}


/**
  * Created by Hierro on 4/26/16.
  */
class EventExample {

}

case class NewPost(text: String, id: Long)
case class BlogPosted(id: Long)
case class BlogNotPosted(id: Long, reason: String)

sealed trait Event
case class PostCreated(text: String) extends Event
case object QuotaReached extends Event
case class PostPublished(id: Long)
case class PublishPost(text: String, id: Long)

case class State(posts: Vector[String], disabled: Boolean) {
  def updated(e: Event): State = e match {
    case PostCreated(text) => copy(posts = posts :+ text)
    case QuotaReached => copy(disabled = true)
  }
}

/**
  * receive method already implemented for us by default
  * It's task delegated to two new methods: `receiveCommand` and `receiveRecover`
  *
  * */
class UserProcessor extends PersistentActor {
  override def persistenceId: String = "123456"

  var state = State(Vector.empty, false) //defines stateful behavior of Actor

  override def receiveCommand: Receive = {
    /*case NewPost(text, id) =>
      if (state.disabled) sender ! BlogNotPosted(id, "quota reached")
      else {
        persist(PostCreated(text)) { event =>
          updateState(event)
          sender ! BlogPosted(id)
          persist(QuotaReached)(updateState)
        }
      }*/
    case NewPost(text,id) =>
      if(!state.disabled) { //check state first
        val created = PostCreated(text) //create event
        updateState(created) //update states accordingly
        updateState(QuotaReached) //update states accordingly
        persistAsync(created)(event => sender ! BlogPosted(id)) //persist event and then respond to client that it has been posted
        //not sure while the slides exclided `event =>` above
        persistAsync(QuotaReached)(_ => ()) //nothing to do here because we already changed the state above
      } else sender ! BlogNotPosted(id, "quota reached")

  }


  def updateState(e: Event): Unit = { state = state.updated(e)}

  override def receiveRecover: Receive = { case e: Event => updateState(e)} //will replay everything when the actor recovers
}


class UserProcessorSimplified(publisher: ActorPath) extends PersistentActor with AtLeastOnceDelivery {

  override def receiveCommand: Receive = {
    case NewPost(text,id) =>
      persist(PostCreated(text)) { e =>
        deliver(publisher, PublishPost(text, _)) //2nd arg is correlation id //actor remember it needs to PublishPost because it sees the PostCreated Message above
        sender ! BlogPosted(id)
      }

    case PostPublished(id) => confirmDelivery(id) //confirms to at-least-once delivery that this id has been confirmed
  }

  override def receiveRecover: Receive = {
    case PostCreated(text) => deliver(publisher, PublishPost(text,_)) //important to note delivery must be redone after crash
    case PostPublished(id) => confirmDelivery(id) //confirms to at-least-once delivery that this id has been confirmed - as above
  }

}

//Example of Exactly-Once Delivery
class Publisher extends PersistentActor {
  var expectedId = 0L

  override def persistenceId: String = "123456"

  override def receiveCommand: Receive = {
    case PublishPost(text,id) =>
      if(id > expectedId) () //ignore, not ready yet. - id is from the future
      else if(id < expectedId) sender ! PostPublished(id) //this ID was already confirmed but got lost so we're confirming again
      else persist(PostPublished(id)) { e =>
        sender ! e //if Actor crashes here then website will not be modified because recover will only increment expectedId
        //modify website
        expectedId += 1 //increment expectedId to receive next PublishedPost command
      }
  }

  override def receiveRecover: Receive = { case PostPublished(id) => expectedId = id + 1} //keep track of what we've already confirmed
}