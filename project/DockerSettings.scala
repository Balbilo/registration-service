import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.SbtNativePackager.Docker
import sbt.Keys._

object DockerSettings {

  val settings = Seq(
    Docker / packageName := (Docker / name).value,
    Docker / version := "1.0.0",
    Docker / maintainer := "Balbilo Devs",
    dockerBaseImage := "openjdk:11.0.9.1-jdk",
    dockerExposedPorts := Seq(8080)
  )

}
