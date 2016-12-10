val akkaVersion = "2.4.11"
val scalaVer = "2.11.8"

val project = Project(
  id = "rzethon-mars-expedition-stream",
  base = file(".")
)
  .settings(
    name := "rzethon-mars-expedition-stream",
    version := "1.0",
    scalaVersion := scalaVer,

    libraryDependencies ++=
      Seq(
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
        "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
        "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.4",
        "de.heikoseeberger" %% "akka-sse" % "2.0.0-RC1",
        "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
        "ch.qos.logback" % "logback-classic" % "1.1.7",
        "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
        "org.scalatest" %% "scalatest" % "3.0.0" % "test",
        "org.scala-lang" % "scala-reflect" % scalaVer
      )
  )