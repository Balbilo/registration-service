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

lazy val model = Projects.module("model")

lazy val domain = Projects.module("domain")
  .dependsOn(model)
  .settings(Dependencies.domain)

lazy val http = Projects.module("http")
  .dependsOn(domain)
  .settings(Dependencies.http)

lazy val projects: Seq[ProjectReference] = Seq(
  model,
  domain,
  http
)
