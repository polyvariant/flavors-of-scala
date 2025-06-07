val TapirVersion = "1.11.33"

lazy val root = (project in file("."))
  .settings(
    name := "tapir-netty",
    version := "0.1.0",
    scalaVersion := "3.3.6",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-core" % TapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-netty-server-cats" % TapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % TapirVersion,
      "org.typelevel" %% "cats-effect" % "3.6.1",
      "io.circe" %% "circe-generic" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10",
      "org.typelevel" %% "weaver-cats" % "0.9.0" % Test,
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub4-server" % TapirVersion,
      "com.softwaremill.sttp.client4" %% "core" % "4.0.8" % Test
    )
  )
