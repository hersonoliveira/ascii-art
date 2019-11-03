import sbt._

object Dependencies {

  val scrimageVersion = "2.1.8"
  val slf4jVersion = "1.7.28"

  val utilDependencies: Seq[ModuleID] = Seq(
    "com.sksamuel.scrimage" %% "scrimage-core" % scrimageVersion,
    "com.sksamuel.scrimage" %% "scrimage-io-extra" % scrimageVersion,
    "com.sksamuel.scrimage" %% "scrimage-filters" % scrimageVersion,
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.slf4j" % "slf4j-simple" % slf4jVersion
  )

}
