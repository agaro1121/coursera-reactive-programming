package week4

import rx.lang.scala.subjects.{AsyncSubject, BehaviorSubject, PublishSubject, ReplaySubject}

/**
  * Created by Hierro on 4/24/16.
  */
object PublishSubjects extends App{

  val channel = PublishSubject[Int]()

  val a = channel.subscribe(x => println(s"a: $x"))
  val b = channel.subscribe(x => println(s"b: $x"))

  channel.onNext(42)

  a.unsubscribe()

  channel.onNext(4711)

  channel.onCompleted() //nothing happens after this
  val c = channel.subscribe(x => println(s"c: $x"))
  channel.onNext(13)


}

object ReplaySubjects extends App{

  val channel = ReplaySubject[Int]()

  val a = channel.subscribe(x => println(s"a: $x"))
  val b = channel.subscribe(x => println(s"b: $x"))

  channel.onNext(42)
  a.unsubscribe()

  channel.onNext(4711)
  channel.onCompleted()

  val c = channel.subscribe(x => println(s"c: $x"))
  channel.onNext(13) //will show everything cuz this subject picks keeps track of history
}

object AsyncSubjects extends App{

  val channel = AsyncSubject[Int]()

  val a = channel.subscribe(x => println(s"a: $x"))
  val b = channel.subscribe(x => println(s"b: $x"))

  channel.onNext(42)
  a.unsubscribe()

  channel.onNext(4711)
  channel.onCompleted()

  val c = channel.subscribe(x => println(s"c: $x"))
  channel.onNext(13) //will show 4711 because that was final value before complete.
  // a will not print here because it unsubscribes before last value is published
}

object BehaviorSubjects extends App{

  val channel = BehaviorSubject[Int]()

  val a = channel.subscribe(x => println(s"a: $x"))
  val b = channel.subscribe(x => println(s"b: $x"))

  channel.onNext(42)
  a.unsubscribe()

  channel.onNext(4711)
  channel.onCompleted()

  val c = channel.subscribe(x => println(s"c: $x"))
  channel.onNext(13) //will print nothing because only latest value is cached. Not final
}

