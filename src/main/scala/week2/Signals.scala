package week2

import scala.util.DynamicVariable

/**
  * Created by anthonygaro on 4/21/16.
  */
class Signal[T](expr: => T){
  import Signal._
  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  update(expr)

  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr())
    if(myValue != newValue){
      myValue = newValue
      val obs = observers
      observers = Set()
      obs.foreach(_.computeValue())
    }

  }

  def apply() = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition") //deals with s() = s() + 1...error would still get caught but it would be stack over flow error instead
    myValue
  }
}

object Signal {
  val caller = new DynamicVariable[Signal[_]](NoSignal) //thread-local state
  def apply[T](expr: => T) = new Signal(expr)
}



object NoSignal extends Signal[Nothing](???) {
  override def computeValue() = ()
}


class Var[T](expr: => T) extends Signal[T](expr) {
 override def update(expr: => T): Unit = super.update(expr)

}
object Var {
  def apply[T](expr: => T) = new Var(expr)
}


class BankAccountWithSignal extends Publisher {
  val balance = Var(0)

  def deposit(amount: Int): Unit = {
    if (amount > 0) {
      val b = balance()
      balance() = b + amount
    }
  }

  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance()) {
      val b = balance()
      balance() = b - amount
    } else throw new Error("insufficient funds")
  }
}

object SignalsTest extends App{

  val d = Var(0)
  val e = Var(1)
  val f = d() + e()
  println(f)

  val a = new BankAccountWithSignal
  val b = new BankAccountWithSignal
  val c = consolidated(List(a,b))

  println(c())

  a deposit 20

  println(c())

  b deposit 30

  println(c())

  def consolidated(list: List[BankAccountWithSignal]): Signal[Int] = {
    Signal(list.map(_.balance()).sum)
  }
}