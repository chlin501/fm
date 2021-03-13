name := "fm"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "org.rogach" %% "scallop" % "4.0.2",
  "dev.zio" %% "zio" % "1.0.5",
  "dev.zio" %% "zio-nio" % "1.0.0-RC10",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "org.slf4j" % "slf4j-simple" % "1.7.30",
  "org.scalatest" %% "scalatest" % "3.2.5" % "test"
)
