import sbt.addCommandAlias

object Aliases {

  val commonAliases = scalaFmt ++ scalaFix

  private lazy val scalaFmt =
    addCommandAlias("runFmt", "scalafmtAll; scalafmtSbt") ++
      addCommandAlias("checkFmt", "scalafmtCheckAll; scalafmtSbtCheck")

  private lazy val scalaFix =
    addCommandAlias("runFix", "all compile:scalafix test:scalafix") ++
      addCommandAlias("checkFix", "compile:scalafix --check ; test:scalafix --check")
}
