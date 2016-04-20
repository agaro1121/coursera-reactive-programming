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

######Week 7
Lecture 1 - Actors are Distributed
Paths: 
akka://HelloWorld/user/greeter -> "akka://HelloWorld/" = authority, user/greeter = path 
akka.tcp://HelloWorld@10.2.4.6:6565 -> HelloWorld actor system at IP and port
akka.tcp://HelloWorld@10.2.4.6:6565/user/greeter

Actor Ref vs Actor Path
- Actor names unique within parent but can be reused once Terminated (new actor might not be the same)
- ActorPath -> Full Name. Exists whether Actor exists or not -> Example: Communicating via Network. Actor may not be there
- ActorRef -> Points to 1 incarnation of Actor Path
- ActorPath cannot be watched because Actor may not exist
- Biggest difference:
ActorRef  = akka://HelloWorld/user/greeter#43428347 -> UID
ActorPath = akka://HelloWorld/user/greeter

```scala
import akka.actor.{ Identify, ActorIdentity }
 
case class Resolved(path: ActorPath)
case class Resolved(path: ActorPath, ref: ActorRef)
case class NotResolved(path: ActorPath)
 
class Resolver extends Actor {
    def receive = {
        case Resolve(path) =>
            context.actorSelection(path) ! Identify((path,sender))            
        case Resolved(path, Some(ref)) => 
            client ! Resolved(path,ref)                
        case NotResolved(path) => 
            client ! NotResolved(path)
    }
}
```

Cluster
- All members must be on same page
- Starts with 1 node joining to itself
- Once all nodes know about the node, then it is officially part of it
- information spread using Gossip Protocol
- No central leader - Any node can be the leader. Node addresses are sorted. 1st one is considered the leader

Config:
- "com.typesafe.akka" %% "akka-cluster" % "version"
app.conf = 
akka {
    actor {
        provider = akka.cluster.ClusterActorRefProvider        
    }    
}
OR 
-Dakka.actor.provider=akka.actor.provider=akka.cluster.ClusterActorRefProvider
-Dakka.cluster.min-nr-of-members=2
-Dakka.remote.netty.tcp.port=0  #automatic
-Dakka.cluster.auto-down=on