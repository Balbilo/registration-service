import sbt.Keys._
import sbt._

object Dependencies {

  private object Versions {
    val catsCore       = "2.2.0"
    val scalaTest      = "3.2.2"
    val scalaCheck     = "1.14.3"
    val scalaPlusCheck = "3.1.0.0-RC2"
    val circe          = "0.13.0"
    val akkaHttp       = "10.2.1"
    val akka           = "2.6.10"
  }

  private object Libraries {

    object Circe {
      val core    = "io.circe" %% "circe-core"    % Versions.circe
      val generic = "io.circe" %% "circe-generic" % Versions.circe
      val parser  = "io.circe" %% "circe-parser"  % Versions.circe
      val all     = Seq(core, generic, parser)
    }

    object Cats {
      val core = "org.typelevel" %% "cats-core" % Versions.catsCore
      val all  = Seq(core)
    }

    object ScalaTest {
      val scalaTest      = "org.scalatest"     %% "scalatest"                % Versions.scalaTest      % Test
      val scalaCheck     = "org.scalacheck"    %% "scalacheck"               % Versions.scalaCheck     % Test
      val scalaPlusCheck = "org.scalatestplus" %% "scalatestplus-scalacheck" % Versions.scalaPlusCheck % Test
      val all            = Seq(scalaTest, scalaCheck, scalaPlusCheck)
    }

    object Akka {
      val http        = "com.typesafe.akka" %% "akka-http"           % Versions.akkaHttp
      val actor       = "com.typesafe.akka" %% "akka-actor"          % Versions.akka
      val sl4j        = "com.typesafe.akka" %% "akka-slf4j"          % Versions.akka
      val stream      = "com.typesafe.akka" %% "akka-stream"         % Versions.akka
      val httpTestKit = "com.typesafe.akka" %% "akka-http-testkit"   % Versions.akkaHttp % Test
      val streamTest  = "com.typesafe.akka" %% "akka-stream-testkit" % Versions.akka     % Test
      val testKit     = "com.typesafe.akka" %% "akka-testkit"        % Versions.akka     % Test
      val all         = Seq(http, actor, sl4j, stream, httpTestKit, testKit, streamTest)
    }
  }

  lazy val json = libraryDependencies ++= Libraries.Circe.all

  lazy val domain = libraryDependencies ++= Libraries.Cats.all

  lazy val testKit = libraryDependencies ++= Libraries.ScalaTest.all

  lazy val http = libraryDependencies ++= Libraries.Cats.all ++ Libraries.Akka.all

}
