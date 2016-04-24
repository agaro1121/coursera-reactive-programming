package week3

import scala.concurrent.{Future, Promise}

/**
  * Created by Hierro on 4/23/16.
  */
object Promises extends App with Future{

  def filter[T](pred: T => Boolean): Future[T] = {
    val p = Promise[T]()

    this onComplete{

    }

    p.future
  }

}
