import Dependencies._

lazy val uidGenerator = (project in file("."))
  .enablePlugins(JavaServerAppPackaging,
    GitVersioning,
    BuildInfoPlugin,
    JavaAppPackaging,
    DockerPlugin,
    AshScriptPlugin)
  .settings(name := s"${Common.projectName}")
  .settings(version := "0.0.1")
  .settings(scalaVersion := "2.12.8")
  .settings(Common.settings: _*)
  .settings(buildInfoKeys := Common.buildInfoKeys)
  .settings(fork in run := true)
  .settings(libraryDependencies ++= deps)