#Reactive Programming

- event-driven(messages?)
- scalable
- resilient
- responsive


##Event-Driven - reacts to events
- Systems built from loosely coupled event handlers
- events can be handled asynchronously without blocking


##Scalable - react to load
- scale up - make use of multiple cores
- scale out - make use of more server nodes
- minimize shared mutable state - will help scalability
- location transparency - shouldn't matter where location is located. Local or remote, functionality should remain the same
- resilience - should deal with failure when multiple nodes are involved

##Resilient - react to failure
- should be able to recover quickly from failures(software,hardware,connection)
- Should be part of initial design, not afterthought
- Needs:
 1. loose coupling
 2. strong encapsulation of state
 3. pervasive supervisor hierarchies


##Responsive - react to users
- shouldn't matter if there's load or failures

```scala
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
```
###Note: `type JBinding = (String,JSON)`
- Taken by itself, the expression is not typable:
`case (key,value) => key + ":" + show(value)`
- The map function expects something like:
`JBinding => String` == `scala.Function1[JBinding,String]`
- We need to prescribe a type from the outside: `type JBinding = (String,JSON)`
- Ultimately this is what it becomes:
```scala
    new Function1[JBinding,String] {
        def apply(x: JBinding) = x match { //this is why JBinding needs to be declared
          case (key,value) => key + ":" + show(value)
        }
    }
```

- Arrays in scala are treated as Sequences by means of implicit wrapper