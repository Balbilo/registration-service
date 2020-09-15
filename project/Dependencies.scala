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
    val mongoDriver = "4.1.0"
  }

  private object Libraries {

    val mongoDriver = "org.mongodb" % "mongodb-driver-reactivestreams" % Versions.mongoDriver

    object Cats {
      val core = "org.typelevel" %% "cats-core" % Versions.catsCore
      val all = Seq(core)
    }

    object Test {
      val scalaTest = "org.scalatest"          %% "scalatest"                % Versions.scalaTest
      val scalaCheck = "org.scalacheck"        %% "scalacheck"               % Versions.scalaCheck
      val scalaPlusCheck = "org.scalatestplus" %% "scalatestplus-scalacheck" % Versions.scalaPlusCheck
      val all = Seq(scalaTest, scalaCheck, scalaPlusCheck)
    }

    object Akka {
      val http = "com.typesafe.akka"        %% "akka-http"         % Versions.akkaHttp
      val actor = "com.typesafe.akka"       %% "akka-actor"        % Versions.akka
      val sl4j = "com.typesafe.akka"        %% "akka-slf4j"        % Versions.akka
      val httpTestKit = "com.typesafe.akka" %% "akka-http-testkit" % Versions.akkaHttp
      val testKit = "com.typesafe.akka"     %% "akka-testkit"      % Versions.akka
      val main = Seq(http, actor, sl4j)
      val test = Seq(httpTestKit, testKit)
    }
  }


  lazy val domain = libraryDependencies ++= Libraries.Cats.all ++ Libraries.Test.all.map(_ % Test)

  lazy val database = libraryDependencies ++= Seq(Libraries.mongoDriver)

  lazy val http = libraryDependencies ++= Libraries.Cats.all ++ Libraries.Test.all.map(
    _ % Test) ++ Libraries.Akka.main ++ Libraries.Akka.test.map(_ % Test)

}
