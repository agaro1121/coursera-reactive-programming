package week5.lecture2_message_processsing_semantics

import akka.actor.Actor
import akka.event.LoggingReceive

/**
  * Created by Hierro on 3/4/16.
  */
class BankAccount extends Actor {

  import BankAccount._

  var balance = BigInt(0)

  override def receive: Receive = LoggingReceive {
    case Deposit(amount) =>
      balance += amount
      sender ! Done

    case Withdraw(amount) if amount <= balance  =>
      balance -= amount
      sender ! Done

    case _ => sender ! Failed
  }

}

object BankAccount {

  case class Deposit(amount: BigInt) {
    require(amount > 0)
  }

  case class Withdraw(amount: BigInt) {
    require(amount > 0)
  }

  case object Done

  case object Failed

}
