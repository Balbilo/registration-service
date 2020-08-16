import sbt.Keys._
import sbt._

object Dependencies {

  private object Versions {
    val catsCore = "2.1.1"
    val scalaTest = "3.2.0"
    val scalaCheck = "1.14.3"
    val scalaPlusCheck = "3.1.0.0-RC2"
    val akkaHttp = "10.2.0"
    val akka = "2.6.8"
  }

  private object Libraries {

    object Cats {
      val core = "org.typelevel" %% "cats-core" % Versions.catsCore
      val all = Seq(core)
    }

    object Test {
      val scalaTest = "org.scalatest"          %% "scalatest"                % Versions.scalaTest      % "test"
      val scalaCheck = "org.scalacheck"        %% "scalacheck"               % Versions.scalaCheck     % "test"
      val scalaPlusCheck = "org.scalatestplus" %% "scalatestplus-scalacheck" % Versions.scalaPlusCheck % "test"
      val all = Seq(scalaTest, scalaCheck, scalaPlusCheck)
    }

    object Akka {
      val http = "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp
      val actor = "com.typesafe.akka" %% "akka-actor" % Versions.akka
      val sl4j = "com.typesafe.akka" %% "akka-slf4j" % Versions.akka
      val httpTestKit = "com.typesafe.akka" %% "akka-http-testkit" % Versions.akkaHttp % "test"
      val testKit =  "com.typesafe.akka" %% "akka-testkit" % Versions.akka % "test"
      val all = Seq(http,actor,sl4j,httpTestKit,testKit)
    }
  }

  lazy val domain = libraryDependencies ++= Libraries.Cats.all ++ Libraries.Test.all

  lazy val http = libraryDependencies ++= Libraries.Cats.all ++ Libraries.Test.all ++ Libraries.Akka.all

}
