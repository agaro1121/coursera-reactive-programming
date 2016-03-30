package week5.lecture2_message_processsing_semantics

import akka.actor.{ActorRef, Actor}
import akka.event.LoggingReceive

/**
  * Created by Hierro on 3/4/16.
  */
class WireTransfer extends Actor{
  import WireTransfer._

  override def receive: Receive = LoggingReceive {
    case Transfer(from,to,amount) =>
      from ! BankAccount.Withdraw(amount)
      context.become(awaitFrom(to,amount,sender))

      def awaitFrom(to: ActorRef, amount: BigInt, customer: ActorRef): Receive = LoggingReceive {
        case BankAccount.Done =>
          to ! BankAccount.Deposit(amount)
          context.become(awaitTo(customer))
        case BankAccount.Failed =>
          customer ! Failed
          context.stop(self)
      }

      def awaitTo(customer: ActorRef): Receive = LoggingReceive {
        case BankAccount.Done =>
          customer ! Done
          context.stop(self)
      }
  }
}


object WireTransfer {
  case class Transfer(from: ActorRef,  to: ActorRef, amount: BigInt)
  case object Done
  case object Failed
}