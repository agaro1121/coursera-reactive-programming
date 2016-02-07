for (i <- 1 until 3; j <- "abc") println(s"$i$j")
(1 until 3) foreach (i => "abc" foreach(j => println(s"$i$j")))

println("hello")