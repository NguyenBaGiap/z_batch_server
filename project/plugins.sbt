// The Play plugin
//addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.6")

// Build application packages
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.19")

// Quality control
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// Scalariform
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")

// Flywaydb
addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")

// Test code coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

resolvers += "Flyway" at "https://flywaydb.org/repo"
