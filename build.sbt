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

lazy val testKit = Projects
  .module("test-kit")
  .dependsOn(model)
  .settings(Dependencies.testKit)

lazy val json = Projects
  .module("json")
  .dependsOn(model)
  .dependsOn(testKit % "test -> test")
  .settings(Dependencies.json)

lazy val domain = Projects
  .module("domain")
  .dependsOn(model)
  .dependsOn(testKit % "test -> test")
  .settings(Dependencies.domain)

lazy val http = Projects
  .module("http")
  .dependsOn(domain)
  .dependsOn(testKit % "test -> test")
  .settings(Dependencies.http)

lazy val projects: Seq[ProjectReference] = Seq(
  model,
  domain,
  json,
  http
)
