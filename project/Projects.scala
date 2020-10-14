import sbt._

object Projects {

  def module(name: String) =
    Project(name, file(name))
      .settings(ScalacOptions.inCompile)

}
