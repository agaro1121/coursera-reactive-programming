package week4

import rx.lang.scala.Subscription
import rx.lang.scala.subscriptions.{CompositeSubscription, MultipleAssignmentSubscription, SerialSubscription}

/**
  * Created by Hierro on 4/24/16.
  */
object Subcriptions extends App{

  val subscription = Subscription(println("bye bye"))

  println("subscription="+subscription)
  println(subscription.isUnsubscribed)
  subscription.unsubscribe()
  println(subscription.isUnsubscribed)

  subscription.unsubscribe()
  println(subscription.isUnsubscribed)
}

object CompositeSubscriptions extends App {
  val a = Subscription(println("A"))
  val b = Subscription(println("B"))

  val composite = CompositeSubscription(a,b)

  println(composite.isUnsubscribed)

  composite.unsubscribe()

  println(composite.isUnsubscribed)
  println(a.isUnsubscribed)
  println(b.isUnsubscribed)

  composite += Subscription(println("C"))
  println(composite.isUnsubscribed)


}

object MultiAssignmentSubscriptions extends App {
  val a = Subscription(println("A"))
  val b = Subscription(println("B"))

  val multi = MultipleAssignmentSubscription()

  println(multi.isUnsubscribed)

  multi.subscription = a
  multi.subscription = b //replaces A with B

  multi.unsubscribe() //unsubscribe B because it was the last one in there
  println(multi.isUnsubscribed)

  multi.subscription = Subscription(println("C")) //at this point multi is unsubscribe and will unsubscribe any new subscriptions
  println(multi.isUnsubscribed)
}

object SerialSubscriptions extends App {
  val a = Subscription(println("A"))
  val b = Subscription(println("B"))

  val serial = SerialSubscription()

  println(serial.isUnsubscribed)

  serial.subscription = a
  serial.subscription = b //unsubscribes A first then subscribes B. Console will show "A" here bcuz it's callback is called println("A")

  serial.unsubscribe() //unsubscribe B because it was the last and only one in there
  println(serial.isUnsubscribed)

  serial.subscription = Subscription(println("C")) //at this point serial is unsubscribe and will unsubscribe any new subscriptions
  println(serial.isUnsubscribed)
}
