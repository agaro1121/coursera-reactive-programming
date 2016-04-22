#The Observer Pattern

Variants: 
- publish/subscribe
- MVC

The Good:
- Decouples view from state
- Allows to have a varying number of views of a given state
- Simple to set up

The Bad:
- Forces imperative style, since handlers are Unit-typed
- Many moving parts that need to be coordinated i.e publish/subcribe
- Concurrency makes this complicated i.e 2 models updating a view at the same time(possible race condition)
- Views tightly bound to one state; view update happens immediately

#Functional Reactive Programming
- Reactive programming is about reacting to sequences of events that happen in time
- Functional view: Aggregate an event sequence into a signal
  
- Signal is a value that changes over time
- It is represented by a function time -> value domain
- instead of propagating updates to mutable state, we define new signals in terms of existing ones

Note: When a signal is changed, it's value changes for it's entire timeline. Past and present

#Thread-Local State - solution to global state but yet is still fragile and does not work in every situation
- Problem with global state is concurrent access. Solution: Synchronization
- Synchronization is slow and can lead to deadlocks. Solution: thread-local state
- Thread-local state means each thread accesses a separate copy of a variable
- Scala API: scala.util.DynamicVariable

##Downsides
- It is imperative by nature, so it produces hidden dependencies that are hard to manage
- JDK implementation involves a global hash table lookup, which can be a performance problem
- Does not play well with situations where threads are multiplexed (task is passed around 
between worker threads so local state does not go with it) between several tasks
 
Another Solution:
- instead of maintaining a thread-local variable, pass its current value into a signal expression as an implicit parameter
- This is purely functional but it currently requires more boilerplate than the thread-local solution
