name := "nous"

version := "2.0.0"

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += "bintray/non" at "http://dl.bintray.com/non/maven"

addCompilerPlugin("org.spire-math" % "kind-projector_2.11" % "0.8.0")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-encoding", "UTF-8",
  "-feature",
  "-language:higherKinds",
  "-language:postfixOps",
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
  "com.github.mpilquist" %% "simulacrum" % "0.7.0",
  "org.scalacheck" %% "scalacheck" % "1.13.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.typelevel" %% "discipline" % "0.5")

