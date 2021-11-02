import sbt._

object Versions {
  val advxml        = "2.4.0"
  val scalatest     = "3.2.9"
  val log4Cats      = "1.2.1"
  val scalaLogging  = "3.9.2"
  val log4Cats2     = "2.1.1"
}

object Library {
  val advxml          = "com.github.geirolz"            %% "advxml-core" % Versions.advxml
  val scalatest      = "org.scalatest"                  %% "scalatest" % Versions.scalatest
  val log4Cats       = "org.typelevel"                  %% "log4cats-core"              % Versions.log4Cats
  val log4CatsSlf4j  = "org.typelevel"                  %% "log4cats-slf4j"             % Versions.log4Cats
  val scalaLogging   = "com.typesafe.scala-logging"     %% "scala-logging"              % Versions.scalaLogging
  val log4Cats2      = "io.chrisdavenport"              %% "log4cats-core"              % Versions.log4Cats2
  val log4CatsSlf4j2 = "io.chrisdavenport"              %% "log4cats-slf4j"             % Versions.log4Cats2
  val logback        = "ch.qos.logback" % "logback-classic" % "1.2.3"
}

object Dependencies {
  import Library._

  val extractStats = Seq(
    advxml,
    log4Cats,
    log4CatsSlf4j,
    logback,
    scalaLogging,
    scalatest % Test
  )
}