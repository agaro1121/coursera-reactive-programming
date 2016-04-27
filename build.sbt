name := "coursera-reactive-programming"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.2"
libraryDependencies += "com.ning" % "async-http-client" % "1.7.19"
libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.0"

libraryDependencies += "io.reactivex" %% "rxscala" % "0.24.0"

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-persistence-tck" % "2.4.2"

