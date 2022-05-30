import sbt._

name := "aips"
version := "0.1"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(name := "aips")
  .settings(
    inThisBuild(
      Seq(
        organization := "com.aips.traffic.signal",
        scalaVersion := "2.12.12",
        scalacOptions ++= Seq(
          "-feature",
          "-deprecation",
          "-unchecked",
          "-Xcheckinit",
          "-Xlint",
          "-Xverify",
          "-Yno-adapted-args",
          "-encoding", "utf8"
        ),
        Test / coverageEnabled := true,
        Compile / coverageEnabled := false,
        Compile / mainClass := Some("com.aips.traffic.signal.TrafficAnalysisMain")
      )
    )
  )
  .settings(Test / parallelExecution := false)
  .settings(
    libraryDependencies ++= (Dependencies.compile ++ Dependencies.test)
  )
  .settings(
    // Assembly settings
    buildInfoPackage := "com.aips.traffic.signal",
    assembly / test  := {},
    assembly / assemblyJarName  := name.value + "-assembly.jar"
  )
