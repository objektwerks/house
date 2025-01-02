name := "house"
organization := "objektwerks"
version := "0.63-SNAPSHOT"
scalaVersion := "3.6.3-RC2"
libraryDependencies ++= {
  val oxVersion = "0.5.8"
  val tapirVersion = "1.11.10"
  val jsoniterVersion = "2.32.0"
  Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-jdkhttp-server" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-jsoniter-scala" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
    "com.softwaremill.ox" %% "core" % oxVersion,
    "org.scalikejdbc" %% "scalikejdbc" % "4.3.2",
    "com.zaxxer" % "HikariCP" % "6.2.1" exclude("org.slf4j", "slf4j-api"),
    "org.postgresql" % "postgresql" % "42.7.4",
    "com.github.blemale" %% "scaffeine" % "5.3.0",
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % Provided,
    "org.jodd" % "jodd-mail" % "7.0.1",
    "com.typesafe" % "config" % "1.4.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "ch.qos.logback" % "logback-classic" % "1.5.15",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wall",
  "-Xmax-inlines",
  "128"
)
