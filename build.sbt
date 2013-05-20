name := "toodledo-api-client"

version := "1.0"

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
    "net.databinder.dispatch" %% "dispatch-core" % "0.10.0",
    "org.slf4j" % "slf4j-simple" % "1.6.4",
    "com.github.scala-incubator.io" % "scala-io-file_2.10" % "0.4.2",
    "net.liftweb" %% "lift-json" % "2.5-M4",
    "joda-time" % "joda-time" % "2.2",
    "org.joda" % "joda-convert" % "1.3.1",
    "org.specs2" %% "specs2" % "1.14"
)
