import scala.sys.process._

ThisBuild / name := "registration-service"
ThisBuild / version := "git rev-parse --short HEAD".!!.trim
ThisBuild / scalaVersion := "2.13.3"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
Test / fork := true
Test / testForkedParallel := true

lazy val team = taskKey[Unit]("Team name")

lazy val root = Project("registration-service", file("."))
  .aggregate(projects: _*)
  .settings(Aliases.commonAliases)
  .settings(team := print("team"))

lazy val http = Projects
  .module("http")
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(Dependencies.http)
  .settings(DockerSettings.settings)

lazy val projects: Seq[ProjectReference] = Seq(
  http
)
