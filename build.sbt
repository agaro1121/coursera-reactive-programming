name := "coursera-reactive-programming"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.2"
libraryDependencies += "com.ning" % "async-http-client" % "1.7.19" force()
libraryDependencies += "io.netty" % "netty" % "3.6.6.Final" force()
libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.0"

libraryDependencies += "io.reactivex" %% "rxscala" % "0.24.0"

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-persistence-tck" % "2.4.2"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-cluster-metrics" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.2"
libraryDependencies += "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.2"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.3"