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
    -Dakka.cluster.min-nr-of-members=2
    -Dakka.cluster.auto-down=on
```

![Remote Actor Setup](/screenshot/remoteActor.png)

1. Fire up ClusterMain ActorSystem
2. Create Receptionist
3. Request comes in and customer gets created
 - -- Remote Next --
 - Customer Deploys Controller into ClusterWorker Remote ActorSystem
 - ClusterWorker has remote guardian
 - When Customer deploys Controller it:
4. Customer sends message to remote guardian to create controller(red dotted line)
5. Remote guardian creates folder to house actors deployed from other systems (`akk.tcp://...` from above)
6. Creates marker for the requested actor (user/app/receptionist/controller) - `user/app/..` from above
7. Creates Controller actor
 - `akk.tcp://...` and `user/app/..` are not actors. They are names inserted to find controller for remote communication
 - logically, controller is child of customer actor
 - controller.context.parent -> customer
8. controller will spawn getters as needed

#Cluster Needs Failure Detection
- consensus unattainable if members are unreachable
- every node is monitored by several others through heartbeats
![Who Watches Who](/screenshot/clusterNeighbors.png)
- lack of heartbeat is sent via gossip protocol
`-Dakka.cluster.auto-down=on` - When node is unreachable, auto-down
![Cluster States](/screenshot/nodeTransitions.png)

##Cluster and Deathwatch
- allows clean-up remote-deployed child actors
- decision must be taken consistently within the cluster
- once Terminated is delivered, the actor cannot come back

##Lifecycle Monitoring is important for distributed fail-over
- delivery of Terminated is guaranteed
- Terminated message can be synthesized by system even if actor is dead

#Eventual Consistency
1. Strong Consistency
 - All reads return updated values - Synchronization blocks
2. Weak Consistency
 - conditions need to be met before reads return updated values - @volatile
3. Eventual Consistency
 - once no more updates are made to an object, there is a time after which all
 reads return the last written value

- an actor forms an island of consistency - everything within an actor is sequentially
consistent
- collaborating actors can at most be eventually consistent - they're always exchanging messages
and that can take time so state is always in flux
- actors not automatically eventually consistent
example: sample implementation had flaws:
1. If merge happened within same millisecond, then merge is not resolved
2. message delivery not guaranteed and there was no resend mechanism
- eventual consistency requires eventual dissemination of all updates - all msgs need to be eventually delivered
this implies a resend mechanism is required. In clustering, messages are resent
pessimistically not out of preparation
- Need to employ suitable data structures ie. CRDTs (Convergent & Commutative (conflict-free) replicated data types)
- some data structures are suitable for collaboration
- an example of this is the cluster states:
1. directed acyclic graph of states - cycles never form on graph. State never goes back to beginning or back to themselves
![Acyclic Nodes](/screenshot/acyclicNodes.png)
2. conflicts can always be resolved locally
- Orders of magnitude can be given to states i.e you can go from leaving to down but not down to leaving so
leaving to down is more important
3. conflict resolution is commutative
- does not matter if you learn green or red first.
- Eventually the merge conflict is down(red)
![Commutative Property](/screenshot/commutativeProperty.png)