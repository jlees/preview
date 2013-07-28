import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "preview"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "pdf" % "pdf_2.10" % "0.5"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += Resolver.url("My GitHub Play Repository", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns)
  )

}
