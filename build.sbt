name := "house"
organization := "objektwerks"
version := "0.4"
scalaVersion := "3.5.0-RC1"
libraryDependencies ++= {
  val jsoniterVersion = "2.28.5"
  Seq(
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % Provided,
    "ch.qos.logback" % "logback-classic" % "1.5.6",
    "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)