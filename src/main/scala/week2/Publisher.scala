package week2

/**
  * Created by anthonygaro on 4/20/16.
  */
trait Publisher {

  private var subcribers: Set[Subscriber] = Set()

  def subscribe(subscriber: Subscriber): Unit = subcribers += subscriber

  def unsubscribe(subscriber: Subscriber): Unit = subcribers -= subscriber

  def publish(): Unit = subcribers.foreach(_.handler(this))

}

trait Subscriber {
  def handler(pub: Publisher)
}


class BankAccount extends Publisher {
  private var balance = 0

  def currentBalance = balance

  def deposit(amount: Int): Unit = {
    if (amount > 0) {
      balance = balance + amount
      publish
    }
  }

  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish()
    } else throw new Error("insufficient funds")
  }

  def getBalance = balance
}

class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach(_.subscribe(this))

  private var total: Int = _
  compute()

  def compute() = total = observed.map(_.currentBalance).sum

  def handler(pub: Publisher) = compute()

  def totalBalance = total
}

object Main extends App {

  val a = new BankAccount
  val b = new BankAccount
  val c = new Consolidator(List(a,b))

  println(c.totalBalance)

  a deposit(20)

  println(c.totalBalance)

  b deposit 30

  println(c.totalBalance)
}