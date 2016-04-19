trait JSON
case class JSeq(elems: Seq[JSON]) extends JSON
case class JObj(bindings: Map[String,JSON]) extends JSON
case class JNum(num: Double) extends JSON
case class JStr(str: String) extends JSON
case class JBool(b: Boolean) extends JSON
case object JNull extends JSON


val jobj = JObj(Map("key" -> JStr("str")))

type JBinding = (String,JSON)

def show(json: JSON): String = json match {
  case JStr(str) => "\"" + str + "\""
  case JNum(num) => num.toString
  case JBool(b) => b.toString
  case JSeq(elems) => "[" + (elems map show mkString ", " ) + "]"
  case JObj(bindings) => {
    val assocs = bindings map {
      case (key, value) => "\"" + key + "\"" + ":" + show(value)
    }
    "{" + (assocs mkString ", ") + "}"
  }
  case JNull => "null"
}

println(show(jobj))

val f: String => String = {case "ping" => "pong"}

f("ping")
//f("abc") //scala.MatchError

val g: PartialFunction[String,String] = {case "ping" => "pong"}
g("ping")
//g("abc") //scala.MatchError
g isDefinedAt "ping"
g isDefinedAt "abc"

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

