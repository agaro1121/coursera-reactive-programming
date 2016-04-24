package week4

import rx.lang.scala.Observable
import scala.concurrent.duration._

/**
  * Created by Hierro on 4/24/16.
  */
object RxOperators extends App{

  val xs: Observable[Int] = Observable.from(List(3,2,1))
  val yss: Observable[Observable[Int]] =
    xs.map{
      x => //every num in xs
        Observable.interval(x seconds) //3 - every 3 seconds, 2 every 2 seconds, 1 every 1 second
          .map(_ => x) // .interval returns a Long. This will convert to Int?
          .take(2)
    }
  val zs: Observable[Int] = yss.flatten //performs a merge internally
  /*
    1
    2
    1
    3
    2
    3
   */
//  val zs: Observable[Int] = yss.concat //buffers internally. Delivers them in order basically. Not streaming
  /* This shows how concat waiting for 3 to come in even though it was pushed every 3 seconds while the others
      where pushed at a faster rate.
      This is a dangerous operation !!!
    output:
    3
    3
    2
    2
    1
    1
   */

  val s = zs.subscribe(println(_))

  readLine()

  s.unsubscribe()
}
