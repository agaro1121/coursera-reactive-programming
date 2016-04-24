package week4

import rx.lang.scala.Observable

import scala.concurrent.duration._

/**
  * Created by Hierro on 4/24/16.
  */
object HelloWorldObservables extends App{

  val ticks: Observable[Long] = Observable.interval(1 seconds)
  val evens: Observable[Long] = ticks.filter(_ % 2 == 0)
  val bufs: Observable[Seq[Long]] = evens.slidingBuffer(count = 2, skip = 1) //groups things
  val s = bufs.subscribe(println(_))

  /* count = 2, skip = 1
    Buffer(0,2)
    Buffer(2,4)
    Buffer(4,6)
    Buffer(6,8)
   */

  /* count = 2, skip = 3
    Buffer(0, 2)
    Buffer(6, 8)
    Buffer(12, 14)
    Buffer(18, 20)
   */

  /* count = 4, skip = 2
    Buffer(0, 2, 4, 6)
    Buffer(4, 6, 8, 10)
    Buffer(8, 10, 12, 14)
   */

  /*
    Buffer(0, 2, 4, 6)
    Buffer(6, 8, 10, 12)
    Buffer(12, 14, 16, 18)
   */

  readLine()

//  scala.io.StdIn.readLong() //readLine() works just the same
  s.unsubscribe()
}
