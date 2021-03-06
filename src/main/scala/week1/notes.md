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

#Monads
Contains:
- flatMap(bind)
- unit
map can be defined with flatMap and f:
m map f == m flatMap (x => unit(f(x)))
        == m flatMap (f andThen unit)
- in scala, map is a primitive function defined on every monad because every monad has a different unit type
so it does not fit the above equation

Examples
- List -> unit(x) -> List(x)
- Set -> unit(x) -> Set(x)
- Option -> unit(x) -> Some(x)
- Generator -> unit(x) -> single(x)



Monad Laws
- Associativity - When held, allows for For Expressions
m flatMap f flatMap g = m flatMap ( x => f(x) flatMap g ) //Put parenthesis wherever and it still works

- Left Unit
unit(x) flatMap f == f(x)
Example: Option
```
Some(x) flatMap f
Some(x) match {
    Some(x) => f(x)
    None => None
    }
```
- Right Unit
m flatMap unit == m
```
opt flatMap Some
opt match{
    Some(x) => Some(x) //Returns exactly what you started with
    None => None //Returns exactly what you started with
    }

###Translation of For Expression
1. A simple for-expression -  eliminates a single generator
`for (x <- e1) yield e2` == `e1.map(x => e2)`

2. A for-expression, where f is a filter and is a (potentially empty) sequence of generators and filters - eliminates a filter element
`for (x <- e1 if f; s) yield e2` = `for(x <- e1.withFilter(x => f); s) yield e2`

3. A for-expression where s is a sequence of generators and filters - eliminates leading generator 
`for (x <- e1; y <- e2; s) yield e3` = `e1.flatMap(x => for (y <- e2; s) yield e3)`

- left-hand side of generator can also be patter
```scala
val data: List[JSON]
for {
JObj(bindings) <- data
JSeq(phones) = bindings("phoneNumbers")
JObj(phone) <- phones
JStr(digits) = phone("number")
if digits startsWith "212"
} yield (bindings("firstName"), bindings("lastName"))
```

```scala
trait Generator[+T] {
    self => //an alias for this
    
    def generate: T
    
    def map[S](f: T => S): Generate[S] = new Generator[S] {
        def generate = f(self.generate)
        //if the above `self.generate` were simply `generate`, then Scala would 
        //expand it to `this.generate` which would refer to the new internal generate function
        //an alternative would to the `self` declaration would be to call `Generator.this.generate` to clarify
    }
}
```