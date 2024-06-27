name := "house"
organization := "objektwerks"
version := "0.12-SNAPSHOT"
scalaVersion := "3.5.0-RC2"
libraryDependencies ++= {
  val jsoniterVersion = "2.30.1"
  Seq(
    "org.scalikejdbc" %% "scalikejdbc" % "4.3.0",
    "com.zaxxer" % "HikariCP" % "5.1.0" exclude("org.slf4j", "slf4j-api"),
    "org.postgresql" % "postgresql" % "42.7.3",
    "com.github.blemale" %% "scaffeine" % "5.2.1",
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % Provided,
    "com.typesafe" % "config" % "1.4.3",
    "ch.qos.logback" % "logback-classic" % "1.5.6",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)