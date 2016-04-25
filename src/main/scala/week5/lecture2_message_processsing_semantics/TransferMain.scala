package week5.lecture2_message_processsing_semantics

import akka.actor.{Props, Actor}
import akka.event.LoggingReceive

/**
  * Created by Hierro on 3/4/16.
  */
class TransferMain extends Actor {
  val accountA = context.actorOf(Props[BankAccount],"accountA")
  val accountB = context.actorOf(Props[BankAccount],"accountB")

  accountA ! BankAccount.Deposit(100)

  override def receive: Receive = LoggingReceive {
    case BankAccount.Done => transfer(50)
//    case BankAccount.Done => transfer(150) //fail case
  }

  def transfer(amount: BigInt) = {
    val transaction = context.actorOf(Props[WireTransfer],"transfer")
    transaction ! WireTransfer.Transfer(accountA,accountB,amount)
    context.become(LoggingReceive{
      case WireTransfer.Done =>
        println("success !")
        context.stop(self)
      case WireTransfer.Failed =>
        println("Failed !")
        context.stop(self)
    })
  }
}
