import scala.sys.process._

ThisBuild / name := "user-service"
ThisBuild / version := "git rev-parse --short HEAD".!!.trim
ThisBuild / scalaVersion := "2.13.3"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
Test / fork := true
Test / testForkedParallel := true

lazy val root = Project("user-service", file("."))
  .aggregate(projects: _*)
  .settings(Aliases.commonAliases)

lazy val http = Projects
  .module("http")
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(Dependencies.http)
  .settings(DockerSettings.settings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)

lazy val projects: Seq[ProjectReference] = Seq(
  http
)
