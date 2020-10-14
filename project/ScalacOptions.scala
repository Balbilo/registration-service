import sbt.Keys._

object ScalacOptions {

  val inCompile = scalacOptions ++=
    Seq(
      "-Yrangepos",        // required by SemanticDB compiler plugin
      "-deprecation",      // Emit warning and location for usages of deprecated APIs.
      "-Wunused:imports",  // Warn if an import selector is not referenced.
      "-Wunused:patvars",  // Warn if a variable bound in a pattern is unused.
      "-Wunused:privates", // Warn if a private member is unused.
      "-Wunused:params",   // Enable -Wunused:explicits,implicits.
      "-Wunused:locals"    // Warn if a local definition is unused.
    )
}
