import sbt._
import sbt.Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "preview"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "pdf" % "pdf_2.10" % "0.5",
    "org.apache.pdfbox" % "pdfbox" % "1.8.2"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += Resolver.url("Pdf from Html plugin", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("PdfBox", url("http://mvnrepository.com/"))(Resolver.mavenStylePatterns)
  )

}
