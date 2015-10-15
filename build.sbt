name := "cobrayaml"

organization  := "org.yaml"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.yaml" % "snakeyaml" % "1.16",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value % "compile",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
