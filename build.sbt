name := "nous"

version := "1.0.0"

scalaVersion := "2.11.6"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += "bintray/non" at "http://dl.bintray.com/non/maven"

addCompilerPlugin("org.spire-math" % "kind-projector_2.11" % "0.5.2")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.github.mpilquist" %% "simulacrum" % "0.3.0"
)


