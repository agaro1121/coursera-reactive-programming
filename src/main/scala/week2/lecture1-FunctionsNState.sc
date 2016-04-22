/**
  * An Object has state if its behavior is influenced by history
  *
  *
  */

class BankAccount {
  private var balance = 0

  def deposit(amount: Int): Unit = {
    if(amount > 0) balance = balance + amount
  }

  def withdraw(amount: Int): Int = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("insufficient funds")
  }

  def getBalance = balance
}
val a = new BankAccount
a deposit 100
a getBalance;
a withdraw 20
a getBalance

class BankAccount2 {
  private var balance = 0

  def deposit(amount: Int): Unit = {
    if(amount > 0) balance = balance + amount
  }

  def withdraw(amount: Int): Int = {
    require(amount > 0 && amount <= balance, "insufficient funds")
      balance = balance - amount
      balance
  }

  def getBalance = balance
}
val b = new BankAccount2
b getBalance;
b withdraw 20
b getBalance
