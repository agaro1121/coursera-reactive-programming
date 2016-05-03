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

#Actor Composition
- Interface of actor is defined by its accepted message types
- type of an actor is structural not nominal
- structure may change over time i.e become()
- actors cannot be composed like functions - they behave more like organizations
- actors are composed on a protocol level

##Customer Pattern
- request/reply
- customer address included in (original) request
- allows dynamic composition of actor systems - for example:
1. alice sends message to bob
2. bob decides who to forward it to
3. it gets processed by the appropriate party and gets sent back directly to Alice
 - This shows bob can dynamically pick where it goes and it still gets back to Alice because the original sender address is attached

##Interceptors
```scala
class AuditTrail(target: ActorRef) extends Actor with ActorLogging {
  def receive = {
    case msg =>
      log.info("sent {} to {}", msg, target)
      target forward msg
  }
}
```

##The Ask Pattern
- You expect exactly one reply
- notice the import statement

**Example 1:**
```scala
import akka.pattern.ask
    class PostsByEmail(userService: ActorRef) extends Actor {
      implicit val timeout = Timeout(3 seconds)
      def receive = {
        case Get(email) =>
          (userSeevice ? findByEmail(email)).mapTo[UserInfo]
            .map(info => Result(info.posts.filter(_.email == email)))
              .recover{ case ex => Failure(ex) }
                .pipeTo(sender)
      }
    }
```

**Example 2:** Aggregating results from multiple responses
```scala
class PostSummary(...) extends Actor {
    implicit val timeout = Timeout(500.millis)
    def receive = {
      case Get(postId, user, password) =>
      val response = for {
        status <- (publisher ? GetStatus(postId)).mapTo[PostStatus]
        text   <- (postStore ? Get(postId)).mapTo[Post]
        auth   <- (authService ? Login(user, password)).mapTo[AuthStatus]
      } yield {
        if(auth.successful) Result(status, text)
        else Failure("not authorized")
      }
      response pipeTo sender
    }
}
```

##Risk Delegation
- Create worker actor to perform dangerous task
- apply lifecycle monitor/supervision
- report success/failure back to requester
- worker actor(temporary actor here) shuts down after each task
**Example:**
```scala
    class FileWriter extends ACtor {
      var workerToCustomer = Map.empty[ActorRef,ActorRef]
      override val supervisorStrategy = SupervisorStrategy.stoppingStrategy

      def receive = {
        case Write(content, file) =>
        val worker = context.actorOf(Props(new FileWorker(contents, file, self)))
          context.watch(worker)
          workerToCustomer += worker -> sender

          case Done         =>        workerToCustomer.get(sender).foreach(_ ! Done) //sender here is worker actor
                                      workerToCustomer -= sender //sender here is worker actor
          case Terminated(worker) =>  workerToCustomer.get(worker).foreach(_ ! Failed)
                                      workerToCustomer -= worker
      }
    }
```

##Facade - other examples...
- translation
- validation
- rate limitation
- access control

#Scalability
- Don't compromise service quality if the # of your clients goes up
##Replication of Actors
- stateless replicas can run concurrently
- Routing messages to worker pools:
- stateful(round robin, smallest queue, adaptive, ...)
- stateless (random, consistent hasing, ...)

##Stateful
###Round-Robin Routing
- equal distribution of messages to routees - messages go to worker actors in the same order
for example: 3 worker actors, messages always hit actors 1 2 3 in that order rinse and repeat
- hiccups or unequal message processing times introduce imbalance
if one worker has an issue and its messages overflow...too bad
- imbalances lead to larger spread in latency spectrum

###Smallest Mailbox Routing - send work to the one with smallest mailbox
- requires routees to be local to inspect message queue
- evens out imbalances, less persistent latency spread
- high routing cost, only worth it for high processing cost - mailbox data structures are expensive to traverse

###Shared Work Queue - takes smallest mailbox routing to the extreme
- requires routees to be local
- most homogeneous latency
- effectively a pull model - routees pull oldest message in the queue
- theres a Balancing Dispatcher for this

###Adaptive Routing
- requires feedback about processing times, latencies, queue sizes, etc
workers periodically send this information to the router for analyzing distribution
- steering the routing weights subject to feedback control theory
    side effects of doing this wrong:
        - oscillations
        - over-dampening

##Stateless
###Random Routing - effectively don't need a router. Sender randomizes destination
- asymptotically equal distribution of messages to routees
- no shared state necessary - low routing overhead
- works with several distributed routers to the same routees
- can stochastically lead to imbalances - random windows of workers getting more work

###Consistent Hashing - effectively don't need a router
- splitting incoming message stream according to some criterion
- bundle substreams and send them to the same reoutees consistently
- can exhibit systematic imbalances based on hashing function
- different latencies for parts of the input stream
- no shared state necessary between different routers to the same targets

####Replication of Stateful Actors
- can be used to replicate stateful actors if substreams correspond to independent parts of the state
- multiple writers to the same state require approprite data structures and are eventually consistent
think eventual consistency and distributed store - 2 actors sharing same `field`
- can be used for fault tolerance more than scalability

####Replication of Persistent Stateful Actors
- based on persisted state
- only one instance active at all times
- consistent routing to the active instance
- possibly buffering messages during recovery
- migration means recovery at a different location
- in the case of event streams, events can be written to both workers and when one goes down,
recovery is faster because state is in both places

#Scalability Summary
- Asynchronous message passing enables vertical scalability - multiple machines
- Location transparency enables horizontal scalability - multiple cores

#Responsiveness
- implies resilience to overload scenarios

##Exploit Parallelism
```scala
class PostSummary(...) extends Actor {
    implicit val timeout = Timeout(500.millis)
    def receive = {
      case Get(postId, user, password) => //below have futures running in parallel
        val status = (publisher ? GetStatus(postId)).mapTo[PostStatus]
        val text   = (postStore ? Get(postId)).mapTo[Post]
        val auth   = (authService ? Login(user, password)).mapTo[AuthStatus]

      val response = for {
        s <- status
        t <- text
        a <- auth
      } yield {
        if(a.successful) Result(s, t)
        else Failure("not authorized")
      }
      response pipeTo sender
    }
}
```
##Load vs Responsiveness
- look at independent parts and see what's happening
- more requests = more latency
- avoid dependency of processing cost on load
- add parallelism elastically (resizing routers) - add it where it's needed

- when the rate exceeds the system's capacity, requests will pile up
- processing gets backlogged
- Clients timeout, leading to unnecessary work being performed

##Circuit Breaker
```scala
class Retriever(userService: ActorRef) extends Actor {
  implicit val timeout = Timeout(2 seconds)
  val cb = CircuitBreaker(context.system.scheduler,
    maxFailures = 3,
    callTimeout = 1 second, //different from timeout above. That's ok because this creates a minimum.
    //If 3 calls IN A ROW take longer than a second, then open up the circuit breaker
    resetTimeout = 30 seconds)

    def receive = {
      case Get(user) =>
        val result = cb.withCircuitBreaker(userService ? user).mapTo[String] //circuit breaker monitors this ask
        //if 3 calls take longer than 1 second then subsequent calls fail immediately without contacting user service
        //once this happens the cb will allow 1 request per 30 seconds and monitors it. If all goes well cb closes.
        //If it doesn't go well, then it will open again for another 30 seconds
          ...
    }
}
```
- good for segregating 2 components so failures of 1 don't affect another
- not good for separating actors - does not completely isolate actors if they run on the same dispatcher

##Bulkheading
- Separate computing intensive parts from client-facing parts
- Actor isolation is not enough: execution mechanism is still shared
- Dedicate disjoint resources to different parts of the system
Can be achieved by:
- Configuring differnt actors to run on different nodes
- on the same same host but run with different dispatchers `Props[MyActor].withDispatcher["compute-jobs"]`
```
akka.actor.default-dispatcher {
  executor = "fork-join-executor"
  fork-join-executor {
    paralleslism-min = 8      #min threads
    paralleslism-max = 64     #max threads
    paralleslism-factor = 3.0 #times number of CPU cores available
  }
}

compute-jobs.fork-join-executor { //new dispatcher
    paralleslism-min = 4 #
    paralleslism-max = 4 # Exactly 4 threads
}
```

##Failure vs Responsiveness
- detecting failure takes time, usually a timeout
- immediate fail-over requires a backup to be readily available - might be latency in backup taking over
- instant failover is possible in active-active configurations - See Below
![active-active-configuration](/screenshot/active-active-configuration.png)
Example:
- Get back 2 responses and compare, if they're the same, send results to client
- if 1 node doesn't respond in time, request to have it replaced