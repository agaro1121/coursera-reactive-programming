#Actors are Distributed
Compared to in-process communication (local):
- data sharing only by value - stateful things will not be the same across
networks because the history of the thing will be different on the receiving side
- lower bandwidth - not as fast across the network
- higher latency
- partial failure - some things may not arrive/some responses may not come back
- data corruption

- running these processes locally will have the same issues but with less frequency

- Actors are "Location Transparent" - hidden behind ActorRef - they could actually be anywhere

##Actor Paths
- Actor names form the URI's path elements:
```scala
    val system = ActorSystem("HelloWorld")
    val ref = system.actorOf(Props[Greeter], "greeter")
    println(ref.path)
    // prints: akka://HelloWorld/user/greeter
    //authority = akka://HelloWorld
    //path= /user/greeter
```
- Remote address example: `akka.tcp://HelloWorld@10.2.4.6:6565/user/greeter`
- Every actor is identified by at least one URI

##ActorRef vs ActorPath
###ActorRef
Actor names are unique within a parent, but can be reused
- ActorRef points to an actor which has started; an incarnation
- ActorRef can be **WATCHED**
- ActorRef example: `akka://HelloWorld/user/greeter#43428347` same as path but with #uid
###ActorPath
- ActorPath is the full name, **whether the actor exists or not**
- ActorPath can only optimistically send a message
- Cannot be watched because it may or may not exists - especially across the wire

###Relative Actor Paths
- grand-child: `context.actorSelection("child/grandchild")`
- sibling: `context.actorSelection("../sibling")`
- from local root: `contect.actorSelection("/user/app")`
- wildcards: `context.actorSelection("/user/controllers/*")`

##What is a Cluster
- Set of nodes (actor systems) about which all members are in agreement
- can collaborate on a common task

###Formation of a Cluster - through inductive reasoning
- single node can declare itself a cluster ("join itself")
- single node can join a cluster
1. request is sent to any member
2. Once all current members know about the new node, it is declared part of the cluster
- info is spread using gossip protocol - no central leader/coordinator - resilient to failure
config:
```scala
    "com.typesafe.akka" %% "akka-cluster" % "2.4.2"

    akka{
      actor{
        provider = akka.cluster.ClusterActorRefProvider
        #configures actor system to use different mechanism to create actors
        #all calls to context.actorOf(...) will be handled by the provider
      }
    }

    -Dakka.actor.provider=akka.cluster.ClusterActorRefProvider
```

![Remote Actor Setup](/screenshot/remoteActor.png)

