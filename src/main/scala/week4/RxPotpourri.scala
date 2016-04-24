package week4

import rx.lang.scala.Observable
import rx.lang.scala.subjects.AsyncSubject

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by Hierro on 4/24/16.
  */
object RxPotpourri extends App {



}


//rough code to get idea. Already written in RxScala lib
//creates Observable from Future
//Simulates behavior of future because it is never computed twice
//AsyncSubject always returns final value so behavior matches up
object ObservableFromFuture extends App{
  import scala.concurrent.ExecutionContext.Implicits.global
  def apply[T](f: Future[T]): Observable[T] = {
    val subject = AsyncSubject[T]()

    f.onComplete{
      case Failure(e) => { subject.onError(e) }
      case Success(t) => {
        subject.onNext(t)
        subject.onCompleted()
      }
     }
    subject
  }
}

object BlockingObservable extends App {

  val xs: Observable[Long] = Observable.interval(1 second).take(5)

  val ys: List[Long] = xs.toBlocking.toList

  println(ys.mkString(" "))

  val zs: Observable[Long] = xs.sum
  val s: Long = zs.toBlocking.single //.single throws Exception if not exactly one element

  println(s)
}