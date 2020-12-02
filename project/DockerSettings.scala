import com.typesafe.sbt.SbtNativePackager.Docker
import com.typesafe.sbt.packager.Keys._
import sbt.Keys._

object DockerSettings {

  val settings = Seq(
    Docker / packageName :=  "balbilo-registration-service",
    Docker / version := "1.0.0",
    Docker / maintainer := "Balbilo Devs",
    dockerBaseImage := "openjdk:11.0.9.1-jdk",
    dockerExposedPorts := Seq(8080)
  )


}
