package week2

import scala.util.DynamicVariable

/**
  * Created by anthonygaro on 4/21/16.
  */
class Signal[T](expr: => T){
  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  private var caller = new StackableVariable(this)
  update(expr)

  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  def computeValue(): Unit = {
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
//  private val caller = new StackableVariable[Signal[_]](NoSignal) //global state
  private val caller = new DynamicVariable[Signal[_]](NoSignal) //thread-local state
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

/**
  * Sample
  * val caller = new StackableVariable(initialSig)
  * caller.withValue(otherSig){...}
  *
  * */
class StackableVariable[T](init: T) {
  private var values: List[T] = List(init)
  def value: T = values.head
  def withValue[R](newValue: T)(op: => R): R = {
    values = newValue :: values
    try op finally values = values.tail
  }
}


class BankAccountWithSignal extends Publisher {
  val balance = Var(0)



  def deposit(amount: Int): Unit = {
    if (amount > 0) {
      val b = balance()
      balance() = b + amount
      publish
    }
  }

  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance()) {
      val b = balance()
      balance() = b - amount
      publish()
    } else throw new Error("insufficient funds")
  }

  def getBalance = balance
}

object Main extends App{

}