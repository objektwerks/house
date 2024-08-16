name := "house"
organization := "objektwerks"
version := "0.35-SNAPSHOT"
scalaVersion := "3.5.0"
libraryDependencies ++= {
  val jsoniterVersion = "2.30.7"
  Seq(
    "io.helidon.webserver" % "helidon-webserver" % "4.1.0",
    "org.scalikejdbc" %% "scalikejdbc" % "4.3.1",
    "com.zaxxer" % "HikariCP" % "5.1.0" exclude("org.slf4j", "slf4j-api"),
    "org.postgresql" % "postgresql" % "42.7.3",
    "com.github.blemale" %% "scaffeine" % "5.2.1",
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % Provided,
    "org.jodd" % "jodd-mail" % "7.0.1",
    "com.typesafe" % "config" % "1.4.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "ch.qos.logback" % "logback-classic" % "1.5.6",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
