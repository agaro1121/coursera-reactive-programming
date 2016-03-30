######DeathWatch
- registers using context.watch(target)
- unregister using context.unwatch(target)
- received Terminated(target) message
- will not receive messages from target after

```scala
case class Terminated private[akka] (actor: ActorRef)
(val existenceConfirmed: Boolean, //if message came directly from actor or not 
 val addressTerminated: Boolean)
extends AutoReceiveMessage //Akka Marker trait signaling this message is handled by actor context(cannot be delivered after UNwatch has been called & cannot be forwarded)
with PossiblyHarmful
```

######EventStream
- We can send messages to known addresses (actore refs)
- We can also publish to an event stream for an unknown audience
- Ever actor can optionally subscribe to (parts of) the EventStream

```scala
trait EventStream {
    def subscribe(subscriber: ActorRef, topic: Class[_]): Boolean
    def unsubscribe(subscriber: ActorRef, topic: Class[_]): Boolean //from particular topic
    def unsubscribe(subscriber: ActorRef): Unit //from entire EventStream
    def public(event: AnyRef): Unit
}
```