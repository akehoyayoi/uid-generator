import com.typesafe.sbt.SbtGit.git
import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport.Docker
import sbt.Keys._
import sbt._
import sbtbuildinfo._

object Common {
  val projectName: String = "uid-generator"

  val buildInfoKeys: Seq[BuildInfoKey] = Seq[BuildInfoKey](
    name,
    version,
    scalaVersion,
    sbtVersion,
    git.gitHeadCommit,
    git.gitCurrentTags,
    git.formattedShaVersion
  )

  val settings: Seq[Def.Setting[_]] = Seq(
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    resolvers ++= Seq(
      DefaultMavenRepository,
      Resolver.defaultLocal,
      Resolver.mavenLocal,
      Resolver.url("bintray-sbt-plugins", url("https://dl.bintray.com/sbt/sbt-plugin-releases/"))(
        Resolver.ivyStylePatterns),
      Resolver.url("bintray-kipsigman-sbt-plugins", url("http://dl.bintray.com/kipsigman/sbt-plugins"))(
        Resolver.ivyStylePatterns),
      Classpaths.typesafeReleases,
      Classpaths.sbtPluginReleases,
      Resolver.bintrayRepo("kamon-io", "sbt-plugins"),
      "Typesafe repository".at("http://repo.typesafe.com/typesafe/releases/"),
      "Sonatype snapshots".at("http://oss.sonatype.org/content/repositories/snapshots/")
    )
  )
}
