import scala.sys.process._

ThisBuild / name := "registration-service"
ThisBuild / version := "git rev-parse --short HEAD".!!.trim
ThisBuild / scalaVersion := "2.13.3"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
Test / fork := true
Test / testForkedParallel := true

lazy val root = Project("registration-service", file("."))
  .aggregate(projects: _*)
  .settings(Aliases.commonAliases)

lazy val http = Projects
  .module("http")
  .settings(Dependencies.http)

lazy val projects: Seq[ProjectReference] = Seq(
  http
)
