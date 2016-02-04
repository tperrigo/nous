name := "nous"

version := "1.0.0"

scalaVersion := "2.11.6"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += "bintray/non" at "http://dl.bintray.com/non/maven"

addCompilerPlugin("org.spire-math" % "kind-projector_2.11" % "0.5.2")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-encoding", "UTF-8",
  "-feature",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-deprecation",
  "-unchecked",
  "-Xcheckinit",
  "-Xlint",
  "-Xverify",
  "-Xfuture",
  "-Yclosure-elim",
  "-Yinline",
  "-Yno-adapted-args")

libraryDependencies ++= Seq(
  "com.github.mpilquist" %% "simulacrum" % "0.3.0",
  "org.scalacheck" %% "scalacheck" % "1.12.2",
  "org.typelevel" %% "discipline" % "0.2.1")

