import com.typesafe.sbt.SbtScalariform.autoImport.scalariformPreferences
import org.scalastyle.sbt.ScalastylePlugin.autoImport.scalastyle
import sbt.Keys._
import sbt._
import scalariform.formatter.preferences._

object Settings {
  lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

  lazy val commonSettings = Seq(
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq(
      "utf8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      //"-Ywarn-value-discard",
      "-Xfatal-warnings"
    ),
    aggregate in Test := true,
    parallelExecution in Test := true,
    fork in Test := true,
    // For Play - Disable documentation generation and avoid to publish the documentation artifact
    // Original: doc in Compile <<= target.map(_ / "none"),
    sources in (Compile, doc) := Seq.empty,
    publishArtifact in (Compile, packageDoc) := false,
    // scalastyle checks automatically run as part of compile task
    compileScalastyle := scalastyle.in(Compile).toTask("").value,
    (compile in Compile) := ((compile in Compile) dependsOn compileScalastyle).value,
    scalariformPreferences := scalariformPreferences.value
      .setPreference(DoubleIndentConstructorArguments, true)
      .setPreference(DanglingCloseParenthesis, Preserve)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 40)
      .setPreference(CompactControlReadability, false)
      .setPreference(CompactStringConcatenation, false)
      .setPreference(FormatXml, true)
      .setPreference(IndentLocalDefs, false)
      .setPreference(IndentPackageBlocks, true)
      .setPreference(IndentSpaces, 2)
      .setPreference(IndentWithTabs, false)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, false)
      .setPreference(PreserveSpaceBeforeArguments, false)
      .setPreference(RewriteArrowSymbols, false)
      .setPreference(SpaceBeforeColon, false)
      .setPreference(SpaceInsideBrackets, false)
      .setPreference(SpaceInsideParentheses, false)
      .setPreference(SpacesWithinPatternBinders, true)
  )
}
