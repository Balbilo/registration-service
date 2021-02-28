import sbt.Keys._
import sbt._

object Dependencies {

  private object Versions {
    val catsCore            = "2.2.0"
    val scalaTest           = "3.2.2"
    val scalaCheck          = "1.14.3"
    val scalaPlusCheck      = "3.1.0.0-RC2"
    val circe               = "0.13.0"
    val akkaHttp            = "10.2.1"
    val akka                = "2.6.10"
    val logBack             = "1.2.3"
    val typeSafeLogging     = "3.9.2"
    val akkaHttpCirce       = "1.35.2"
    val mongoDriver         = "4.1.1"
    val reactiveMongoDriver = "1.0.2"
    val pureConfig          = "0.14.0"
    val bcrypt              = "4.3.0"
    val jwtCore             = "4.3.0"
  }

  private object Libraries {

    object Utils {
      val bcrypt          = "com.github.t3hnar"          %% "scala-bcrypt"    % Versions.bcrypt
      val pureConfig      = "com.github.pureconfig"      %% "pureconfig"      % Versions.pureConfig
      val logBack         = "ch.qos.logback"              % "logback-classic" % Versions.logBack
      val typeSafeLogging = "com.typesafe.scala-logging" %% "scala-logging"   % Versions.typeSafeLogging
      val jwtCore         = "com.pauldijou"              %% "jwt-core"        % Versions.jwtCore

      val all = Seq(pureConfig, bcrypt, logBack, typeSafeLogging, jwtCore)
    }

    object Mongo {
      val mongoDriver         = "org.mongodb.scala" %% "mongo-scala-driver" % Versions.mongoDriver
      val reactiveMongoDriver = "org.reactivemongo" %% "reactivemongo"      % Versions.reactiveMongoDriver
      val all                 = Seq(mongoDriver, reactiveMongoDriver)
    }

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
      val http        = "com.typesafe.akka" %% "akka-http"         % Versions.akkaHttp
      val httpCirce   = "de.heikoseeberger" %% "akka-http-circe"   % Versions.akkaHttpCirce
      val stream      = "com.typesafe.akka" %% "akka-stream"       % Versions.akka
      val sl4j        = "com.typesafe.akka" %% "akka-slf4j"        % Versions.akka
      val httpTestKit = "com.typesafe.akka" %% "akka-http-testkit" % Versions.akkaHttp % Test
//      val actor       = "com.typesafe.akka" %% "akka-actor"          % Versions.akka
//      val streamTest  = "com.typesafe.akka" %% "akka-stream-testkit" % Versions.akka     % Test
//      val testKit     = "com.typesafe.akka" %% "akka-testkit"        % Versions.akka     % Test
      val all         = Seq(http, httpCirce, stream, sl4j, httpTestKit)
    }

  }

  lazy val http =
    libraryDependencies ++= Libraries.Cats.all ++
      Libraries.Akka.all ++
      Libraries.Circe.all ++
      Libraries.ScalaTest.all ++
      Libraries.Mongo.all ++
      Libraries.Utils.all

}
