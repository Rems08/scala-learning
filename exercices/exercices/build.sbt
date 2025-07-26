ThisBuild / scalaVersion  := "3.7.1"
ThisBuild / organization  := "ch.epfl.scala"
ThisBuild / version       := "1.0"

lazy val javafxVersion = "24"

// Detect host OS once; you are on an Apple-silicon Mac so the value
// will be "mac-aarch64".  Keep the match if you share the project.
lazy val os = "mac-aarch64"

libraryDependencies ++= Seq(
  // ScalaFX wrapper
  "org.scalafx" %% "scalafx" % "24.0.0-R35",
  // Native OpenJFX modules that actually provide `javafx.*`
  "org.openjfx" % s"javafx-base"      % javafxVersion classifier os,
  "org.openjfx" % s"javafx-graphics"  % javafxVersion classifier os,
  "org.openjfx" % s"javafx-controls"  % javafxVersion classifier os,
  "org.openjfx" % s"javafx-fxml"      % javafxVersion classifier os
)