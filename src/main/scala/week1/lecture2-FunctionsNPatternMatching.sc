val f: String => String = {case "ping" => "pong"}

f("ping")
//f("abc") //scala.MatchError

val g: PartialFunction[String,String] = {case "ping" => "pong"}
g("ping")
//g("abc") //scala.MatchError
g isDefinedAt "abc"
g isDefinedAt "ping"

val h: PartialFunction[List[Int], String] = {
   case Nil => "one"
   case x :: rest =>
    rest match {
      case Nil => "two"
     }
   }
//below is true because there IS case for this situation x::rest BUT
//it doesn't mean that what's inside that case won't throw a MatchError
h.isDefinedAt(List(1,2,3))
//h(List(1,2,3)) //scala.MatchError
h(List(1))
//h(List(1,2)) //scala.MatchError

