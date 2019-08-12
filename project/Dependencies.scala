import sbt._

object Dependencies {

  val ScalaTestVersion = "3.0.5"

  lazy val deps = Seq(
    // database
    "com.h2database" % "h2" % "1.4.192",
    "io.getquill" %% "quill-jdbc" % "3.4.1",
    // test
    "org.scalatest" %% "scalatest" % ScalaTestVersion % Test
  )
}
