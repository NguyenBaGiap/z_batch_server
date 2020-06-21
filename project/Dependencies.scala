import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val scalikejdbcVersion = "3.2.3"
  lazy val mysqlVersion = "8.0.15"
  lazy val akkaVersion = "2.5.21"
  lazy val skinnyVersion = "3.0.2"

  val mysqlConnectorJava = "mysql" % "mysql-connector-java" % mysqlVersion

  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion
  val scalikejdbcConfig = "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion
  val scalikejdbcTest = "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test

  val akkaQuartz = "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.0-akka-2.5.x"
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
  val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  val akkaSl4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion

  val skinnyOrm = "org.skinny-framework" %% "skinny-orm" % skinnyVersion
  val skinnyTest = "org.skinny-framework" %% "skinny-test" % skinnyVersion % "test"
  val skinnyFactoryGirl = "org.skinny-framework" %% "skinny-factory-girl" % skinnyVersion % "test"

  val scalajHttp = "org.scalaj" %% "scalaj-http" % "2.4.1"
  val googleApiClient = "com.google.api-client" % "google-api-client" % "1.28.0"
  val googleApiServicesOauth2 = "com.google.apis" % "google-api-services-oauth2" % "v2-rev141-1.25.0"
  val gooleOauthClientJetty = "com.google.oauth-client" % "google-oauth-client-jetty" % "1.28.0"
  val googleApiServicesSheets = "com.google.apis" % "google-api-services-sheets" % "v4-rev543-1.25.0"
  val googleScriptService = "com.google.apis" % "google-api-services-script" % "v1-rev445-1.25.0"
  val googleDriveService = "com.google.apis" % "google-api-services-drive" % "v3-rev110-1.23.0"

  val portDatabaseDependencies = Seq(
    scalikejdbc, scalikejdbcConfig, scalikejdbcTest,
    mysqlConnectorJava,
    skinnyOrm,
    skinnyTest,
    skinnyFactoryGirl
  )

  val portGoogleDependencies = Seq(
    googleApiClient,
    gooleOauthClientJetty,
    googleApiServicesSheets,
    googleScriptService,
    googleDriveService
  )
}
