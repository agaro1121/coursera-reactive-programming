#Failure Handling With Actors
- Resilience demands containment and delegation of failure
- failed actor can be terminated or restarted
- Failure comes in the form of messages
- Messages are serialized, so no other messages are processed while failure message is being handled
- Failed message will not be handled again because it might be the cause of the failure

####OneForOne
####AllForOne
- both can have a simple rate trigger (finite number of restarts)

```scala
OneForOneStrategytrategy(maxNrOfRestarts = 10, withinTimeRange = 1 minute) {
  case _: DBException => Restart //will turn into Stop
}
```

######What does restart mean?
- expected error conditions are handled explicitly
- unexpected error indicate invalidated actor state
- restart will install initial behavior/state

##Actor Lifecycle (Simplified)
- start - only 1
- (restart)* (incarnation)
- stop - only 1 - once stopped, it cannot be restarted

####Actor Lifecycle Hooks
```scala
trait Actor {

  def preStart(): Unit = {}

    /*Default behavior - stop all children because it is considered part of the Actor's state*/
  def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    context.children foreach (context.stop(_))
    postStop() //default
  }

  def postRestart(reason: Throwable): Unit = {
    preStart() //default
  }

  def postStop(): Unit = {} //invoked once Actor is no longer running
}
``

Example1(actor fully reinitlized during restart:

```scala
    classs DBActor extends Actor {
       val db = DB.openConnection(...)
         ...
       override def postStop(): Unit = {
        db.close()
       }
    }
```

Example2(Actor with External State):
```scala
class Listener(source: ActorRef) extends Actor {
  override def preStart() { source ! RegisterListener(self) }
  override def preRestart(reason: Throwable, message: Option[Any]) {} //Do nothing because `source` ActorRef stays valid across lifecycle
  //We also do not stop child actors with this preRestart() method. Context will recursively restart child actors that were not stopped
  override def postRestart(reason: Throwable) {} //no need to re-register because we didn't unregister
  override def postStop(){ source ! UnregisterListener(self) } //obvious
}
```

#Lifecycle Monitoring and the Error Kernel
##DeathWatch
- An Actor can register its interest using context.watch(target)
- It will receive a `Terminated(target)` message when target stops
- `Terminated` message comes with target actor's ActorRef so the watcher can know which one was Terminated
- It will not receive any DIRECT messages from that actor moving forward

```scala
trait ActorContext {
  def watch(target: ActorRef): ActorRef
  def unwatch(target: ActorRef): ActorRef //Terminated message will not be sent once you do this.
                                         // Even if the target actor is in the middle of being terminated. No race condition
}
```
```scala
case class Terminated private[akka] (actor: ActorRef) //actor being watched
(val existenceConfirmed: Boolean, //
 val addressTerminated: Boolean) //is this message created by the system ?
 extends AutoReceiveMessage //Akka Marker trait that says this message is handled by the ActorContext especially
                           // This message cannot be forwarded
    with PossiblyHarmful
 ```

- Parents don't need DeathWatch for it's children.
- Parents will find out if a child actor stopped before the Terminated message would have been sent.

##The Error Kernel Pattern
- Keep important data near the root, delegate risk to leaves
- restarts are recursive (child actors get restarted)
- restarts are more frequent near the leaves
- avoid restarting Actors with important state

```scala
SupervisorStrategytegy.stoppingStrategy //if a child fails -> Stop
```
##The EventStream
```scala
    trait EventStream {
      def subscribe(subscriber: ActorRef, topic: Class[_]): Boolean //this is a Java class. Not a scala class
      def unsubscribe(subscriber: ActorRef, topic: Class[_]): Boolean
      def unsubscribe(subscriber: ActorRef): Boolean
      def publish(event: AnyRef): Unit //same effect as tell(ActorRef)
    }
```

**Example:**
```scala
    class Listener extends Actor {
      context.system.eventStream.subscribe(self, classOf[LogEvent])

      def receive = {
        case e: LogEvent =>   ...
      }

      override def postStop(): Unit = {
        context.system.eventStream.unsubscribe(self)
      }
    }
```

##Where do Unhandled Messages Go?
```scala
trait Actor {
def unhandled(message: Any): Unit = message match {
    case Terminated(target) => throw new DeathPactException(target)
    case msg =>
      context.system.eventStream.publish(UnhandledMessage(msg,sender,self))
  }
}
```

#Persistend Actor System
Two Ways
- in-place updates - files, DB, etc
- changes in append-only fashion - saving deltas

##Benefits of saving current state
- recovery of latest state in constant time

##Benefits of saving changes
- history can be replayed, audited or restored
- Some processing errors can be corrected retroactively
- Additional sight can be gained on business process - i.e buying history and exchanges
- writing an append-only stream optimizes IO bandwidth
- changes are immutable and can freely be replicated

##Persistence Primitive
```scala
    persist(MyEvent(...)) { event => //comes back from journal if it is persisted successfully
      //now <event> is persisted
      doSomethingWith(event)
    }
```
###Persist
- Actor is only states that have been persisted
###PersistAsync
- Updates its state whether event has been persisted now or later.

- use At-Least-Once for successful message delivery - responsibility of sender
- take note that message needs to be resent
- must also note acknowledgment so the actor can stop resending

- Exactly-Once - responsibility of recipient
- receiver has to know what it has already done to avoid redundancy

##When to perform effects?
**Performing the effect and persisting that it was done cannot be atomic**
- Perform it before persisting for at-least-once semantics
- Perform it after persisting for at-most-once semantics

- if processing is idempotent then using at-least-once semantics achieves
effectively exactly-once semantics

##Summary
- Actors persist facts that represent changes to their state
- Events can be replicated and used to inform other components.
- Recovery replays past events to reconstruct state;
snapshots reduce this cost (so ALL events are not replayed from beginning of time)
- snapshots do not represent the meaningful business events of the domain, they represent the internal state of a component
this makes them less durable and less valuable than the events themselves
