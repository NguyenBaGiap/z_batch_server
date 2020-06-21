import sbt._
import Keys._
import Dependencies._
import Settings._

name := "z_batch_server"

version := "1.0"

scalaVersion in ThisBuild := "2.13.2"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .aggregate(application, domain, port, utility)
  .dependsOn(application, domain, port % "test->test;compile->compile", utility)
  .settings(
    libraryDependencies += scalaTest % Test,
    // Copy all config files
    mappings in Universal ++= {
      ((sourceDirectory in Compile).value / "resources" * "*").get.map { f =>
        f -> s"conf/${f.name}"
      }
    }
  )
  .settings(commonSettings)

lazy val application = (project in file("pasteZ/application"))
  .dependsOn(utility % "test->test;compile->compile")
  .dependsOn(domain % "test->test;compile->compile")
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq("org.json4s" %% "json4s-jackson" % "3.5.0",
    "org.slf4j" % "slf4j-api" % "1.7.26",
    "ch.qos.logback" % "logback-classic" % "1.2.3"))

lazy val domain = (project in file("pasteZ/domain"))
  .aggregate(domainBatch, domainNotification)
  .dependsOn(
    domainBatch % "test->test;compile->compile",
    utility % "test->test;compile->compile"
  )
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq("com.google.inject" % "guice" % "4.1.0"))

lazy val domainBatch = (project in file("pasteZ/domain/batchContext"))
  .dependsOn(utility % "test->test;compile->compile")
  .settings(commonSettings)

lazy val domainNotification = (project in file("pasteZ/domain/notificationContext"))
  .dependsOn(utility % "test->test;compile->compile")
  .settings(commonSettings)

lazy val port = (project in file("pasteZ/port"))
  .aggregate(portBatch, portPasteZDatabase, portNotification, portGoogle)
  .dependsOn(
    portBatch % "test->test;compile->compile",
    portPasteZDatabase % "test->test;compile->compile",
    portNotification % "test->test;compile->compile",
    portGoogle % "test->test;compile->compile"
  )
  .settings(commonSettings)

lazy val portBatch = (project in file("pasteZ/port/primary/batch"))
  .dependsOn(utility % "test->test;compile->compile", application % "test->test;compile->compile")
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(akkaQuartz, akkaActor, akkaRemote, akkaTestkit, akkaSl4j, scalikejdbc))

lazy val portPasteZDatabase = (project in file("pasteZ/port/secondary/pasteZDatabase"))
  .dependsOn(utility % "test->test;compile->compile", application % "test->test;compile->compile")
  .settings(
    libraryDependencies ++= portDatabaseDependencies
  )
  .settings(commonSettings)

lazy val portNotification = (project in file("pasteZ/port/secondary/notification"))
  .dependsOn(
    utility % "test->test;compile->compile",
    application % "test->test;compile->compile"
  )
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(scalajHttp))

lazy val portGoogle = (project in file("pasteZ/port/secondary/google"))
  .dependsOn(utility % "test->test;compile->compile", application % "test->test;compile->compile")
  .settings(
    libraryDependencies ++= portGoogleDependencies
  )
  .settings(commonSettings)

lazy val utility = (project in file("pasteZ/utility"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq("joda-time" % "joda-time" % "2.9",
    akkaQuartz, akkaActor, akkaRemote, akkaTestkit, akkaSl4j,"jp.t2v" %% "holidays" % "5.1",
    "com.github.nscala-time" %% "nscala-time" % "2.14.0"))

initialCommands := """
import scalikejdbc._
import skinny.orm._, feature._
import org.joda.time._
skinny.DBSettings.initialize()
implicit val session = AutoSession
"""