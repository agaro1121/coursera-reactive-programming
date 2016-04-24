#Dualization of `Iterable[T]` to `Observable[T]`
- synchronous -> Asynchronous
- trait with single method can be turned into `type` declaration

Example:
- Step 1 - Get rid of hasNext and replace next with `Option[T]`
```scala
    trait Iterable[T] {
    def iterator(): Iterator[T]
  }

      trait Iterator[T] {
        def hasNext: Boolean //can be removed and make next Option
        def next(): Option[T]
      }
```
```scala
      type Iterator[T] = () => Option[T]
```

- Step 2 - Get rid of `Iterator[T]` and just use Iterable
```scala
     trait Iterable[T] {
       def iterator(): () => Option[T]
     }
```

```scala
    type Iterable[T] = () => (() => Try[T])
```

- Now the dualization - reverse arrows
```scala
  type Iterable[T] => (() => Try[Option[T]])
```

```scala
    ( Try[Option[T]] => Unit) => Unit
```

- Now complexify - earlier we took traits and turned them into functions.
Now we do the opposite
```scala
    type Observable[T] = ( Try[Option[T]] => Unit) => Unit
```
- first focus on what's inside parenthesis
```scala
  Try[Option[T]] => Unit
  //this will have 3 cases(when pattern matching):
  Throwable => Unit, () => Unit, T => Unit
  //let's put these in it's own function
  type Observer[T] = (Throwable => Unit, () => Unit, T => Unit)
  type Observable[T] = Observer[T] => Unit
```

- turn into Traits
```scala
    trait Observer[T] = {
        def onError (error: Throwable):Unit
        def onComplete(): Unit
        def onNext(value: T): Unit //unlike futures, this can be called many times.
                                  //This represents a stream
    }

    trait Observable[T] = {
      def subscribe(observer: Observer[T]): Unit
    }
```

#RxOperators
- merge - takes asynchrounes values coming in and merges them into 1 stream
- flatMap - does map then merge
- concat - preserves order

#Subscriptions
- Cold Observable - each subscriber has it's own private source (subscription has side effect)
for example, your own random generator stream and no one else has the same random numbers
- Hot Observable - same source shared by everyone (subscription has no side effect)
- Unsubscribe != cancellation - Just because you unsubscribe does not mean you have to stop the stream

##3 Types:
- Composite - Collection of subscriptions. Unsubscribe together
if additional subscriptions are added after the composite unsubscribes,
then they are automatically unsubscribed
- MultiAssignment - Swap underlying subscription
- Serial - unsubscribe when swapped

#Subjects - equivalent of Promise - Recommended NOT to use this - mutable
- Promise has a Future
- Subject has an Observable
- sort of like a channel

##Different kinds
###PublishSubject - Basically a pipe
- If you subscribe to a channel that is onCompleted already, then the subscriber will
immediately receive onCompleted or onError
- if you sign up late, you miss out on all the history

###ReplaySubject - Has memory of what has happened and will replay if you subscribe late

###Other Types
- AsyncSubject - Caches final value
- BehaviorSubject - Caches latest value

#RxPotpourri
##Notification
- Basically like a try.
- Represents onError, onComplete, onNext
- function `def materialize` - takes observable of T and spits back
Observable[Notification[T]]
-so Just like Future has Try in it, Observable has Notification in it

##Do Not Block
Future Example:
```scala
val f: Future[T] = Future{..}
val t: T = Await.result(f, 10 seconds)
```
Observable Example:
```scala
val o: Observable[T] = Observable{..}
val ts: Observable[T] = o.toBlocking //will block just like Await
//returns blocking observable
ts.forEach(t => {...})
```

##Higher Order Functions
###reduce
- usually not used
- imagine reducing an infinite stream to a scalar value
- returns an Observable with 1 element

###from
- from iterable to observable

####Check wiki for backpressure support

#Observable Contract - Don't ever implement Observer && Observable yourself
- Rx Design Guidelines pdf
- onNext, onCompleted/onError called serially all the time. Never overlap
-