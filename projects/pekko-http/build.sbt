name := "pekko-http-project"

version := "0.1"

scalaVersion := "3.3.6"

val PekkoVersion = "1.1.3"
val PekkoHttpVersion = "1.2.0"

libraryDependencies ++= Seq(
  "org.apache.pekko" %% "pekko-actor-typed" % PekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % PekkoVersion,
  "org.apache.pekko" %% "pekko-http" % PekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-spray-json" % PekkoHttpVersion,
  "org.apache.pekko" %% "pekko-testkit" % PekkoVersion % Test,
  "org.apache.pekko" %% "pekko-http-testkit" % PekkoHttpVersion % Test,
  "org.scalatest" %% "scalatest" % "3.2.9" % Test
)
