for (i <- 1 until 3; j <- "abc") println(s"$i$j")

//translates to:

(1 until 3) foreach (i => "abc" foreach(j => println(s"$i$j")))


//while(true){
//  println("hi")
//}

//do{
//  println("hi")
//}while(true)

