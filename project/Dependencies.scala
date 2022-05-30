import sbt._

object Version {
  val scalatest = "3.2.10"
  val logbackVersion = "1.2.10"
  val scalaLoggingVersion = "3.9.4"
  val scalaArm = "2.0"
}

object Dependencies {

  lazy val compile: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % Version.logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLoggingVersion,
    "com.jsuereth" %% "scala-arm" % Version.scalaArm
  )

  lazy val test: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % Version.scalatest
  ).map(_ % Test)

}
