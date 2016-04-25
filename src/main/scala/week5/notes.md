#Why Actors?

- In Scala, eveyr object has an associated lock
call `obj.synchronized{...}

**Example:**
```scala
class BankAccount extends {
  private var balance = 0
  def currentBalance = balance

  def deposit(amount: Int): Unit = this.synchronized {
    if (amount > 0) {
      balance = balance + amount
    }
  }

  def withdraw(amount: Int): Unit = this.synchronized {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
    } else throw new Error("insufficient funds")
  }
  
}
```

```scala
    def transfer(from: BankAccount, to: BankAccount, amount: Int): Unit = {
        from.synchronized {
            to.synchronized {
                from.withdraw(amount)
                to.deposit(amount)
            }
        }
    }
```
- Solution above introduces Dead-lock
- transfer A->B in one thread
- transfer B->A in another thread
- one lock taken by each, nobody can progress

##We want Non-Blocking Objects
- blocking synchronization introduces dead-locks
- blocking is bad for CPU utilization
- synchronous communication couples sender and receiver

#The Actor Model
Run Configuration:
- main: akka.Main
- akka.loglevel=WARNING

```scala
class Counter2 extends Actor {
  def counter(n: Int): Receive ={
    case "incr" => context.become(counter(n + 1)) //become() function permanently changes behavior to next count n++
    case "get" => sender ! n
  }

  def receive = counter(0)
}
```

#Message Processing Semantics
- messages are sequential/synchronized and does not block due to enqueueing
- akka.actor.debug.receive=ON - activates LoggingReceive blocks so we know when messages are being processed. Shows incoming message
- akka.loglevel=DEBUG
```scala
import akka.event.LoggingReceive

LoggingReceive {...}
```
###Message Delivery Guarantees
- at-most-once:  sending delivers [0,1] times
- at-least-once: resending until acknowledged delivers [1,infinite) times - sender keeps message for buffering for resending
- exactly-once:  processing only first reception delivers 1 time - sender must keep track of what has been processed. Receiver may need to keep state as well.
- Unique correlation ID
- Message Order - akka specific

#Designing Actor Systems

##Scheduler
- Optimized for high volume, short duration, frequent cancellation
- scheduleOnce -> Returns `Cancellable` which can be used to cancel the task
- `Cancellabe` can cause a race condition between cancellation and the task firing.
- task can fire after you requested a cancel

##What We learned so far
- Do not refer to actor state from code running asynchronously i.e Future
- Actors are run by a dispatcher-potentially shared-which can also run Futures
- Prefer context.become for different states, with data local to the behavior

#Testing Actors
- can only verify externally observable effects
- 