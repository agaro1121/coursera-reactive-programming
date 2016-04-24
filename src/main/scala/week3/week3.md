#Monads and Effects

|               | One         | Many          |
| ------------- | ----------- | ------------- |
| Synchronous   | T/Try[T]    | Iterable[T]   |
| Asynchronous  | Future[T]   | Observable[T] |

###Future.fallBackTo(...)
- If the fallback fails, then the error of the original future is returned
- `import scala.language.postfixOps`

####Implementation of retrying using recursion:
```scala
def retry(noTimes: Int)(block: =>Future[T]): Future[T] = {
  if(noTimes == 0){
    Future.failed(new Exception("Sorry"))
  }else{
    block fallbackTo {
      retry(noTimes - 1){ block }
    }
  }
}
```

####Implementation of retrying using FoldLeft:
```scala
    def retry(noTimes: Int)(block: =>Future[T]): Future[T] = {
      val ns = (1 to noTimes).toList
      val attempts = ns.map(_ => ()=>block) //delays execution of block
      //creates the following:
      //attempts = List(()=>block, ()=>block,...)
      val failed = Future.failed(new Exception("boom")) //neutral element
      val result = attempts.foldLeft(failed)
        ((a,block) => a recoverWith{ block() })
      result
      /*
        result = (...((failed recoverWith {block1()})
                    recoverWith{ block2() })
                      recoverWith{ block3() })..)
                        recoverWith{ blocknoTimes() }))

       */
    }
```

####Implementation of retrying using FoldRight:
```scala
    def retry(noTimes: Int)(block: =>Future[T]): Future[T] = {
      val ns = (1 to noTimes).toList
            val attempts = ns.map(_ => ()=>block) //delays execution of block
            //creates the following:
            //attempts = List(()=>block, ()=>block,...)
            val failed = Future.failed(new Exception("boom")) //neutral element
            val result = attempts.foldRight(() =>failed)
              ((block,a) => () => {block() fallbackTo { a() } })
            result()
            /*
              retry(3) { block } ()
              = block1 fallbackTo { block2 fallbackTo {block3 fallbackTo { failed }}}
             */
    }
```

#Async Await - Found in any modern language
- Making effects implicit in a limited scope
- `import scala.async.Async._` ->
when you have code in an async block, then you can use await to get a result
basically allows you to get rid of a Future in a magic way
`async {...await{...} ...}`

####Implementation of retrying using await no recursion:
```scala
def retry(noTimes: Int)(block: =>Future[T]): Future[T] = async { //notice async here
  var i = 0
  var result: Try[T] = Failure(new Exception("..."))
  while(result.isFailure && i < noTimes) {
    result = await {Try(block)}
    i += 1
  }
  result.get
}
```

