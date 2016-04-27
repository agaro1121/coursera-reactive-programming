package week7.greeter

import akka.actor.{ActorSystem, Props}

/**
  * Created by anthonygaro on 4/27/16.
  */
object GreeterMain extends App {
  val system = ActorSystem("HelloWorld")
  val ref = system.actorOf(Props[Greeter],"greeter")
  println(ref.path)
  system.terminate()

}
