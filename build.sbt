name := "SleepFramework"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Mesosphere Repo" at "http://downloads.mesosphere.io/maven"

libraryDependencies ++= Seq(
  "org.apache.mesos" % "mesos" % "0.14.2",
  "mesosphere" % "mesos-utils" % "0.0.6"
)