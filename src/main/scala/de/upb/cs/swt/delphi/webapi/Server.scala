package de.upb.cs.swt.delphi.webapi

import akka.http.scaladsl.server.HttpApp

/**
  * Web server configuration for Delphi web API.
  */
object Server extends HttpApp with SearchFeature {
  private implicit var configuration : Configuration = new Configuration()

  def configure(configuration: Configuration) = {
    this.configuration = configuration
  }


  override def routes =
      path("version") { version } ~
        path("features") { processFeatures } ~
        pathPrefix("search" / Remaining) { query => processSearch(query) } ~
        pathPrefix("retrieve" / Remaining) { identifier => processRetrieve(identifier) }


  private def version = {
    get {
      complete {
        BuildInfo.version
      }
    }
  }

  private def processFeatures = {
    get {
      complete {
        "features"
      }
    }
  }

  def processRetrieve(identifier: String) = {
    get {
      complete(identifier)
    }
  }

  def processSearch(query: String) = {
    get {
      complete {
        //implicit val configuration = this.configuration
        search(query).toString()
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val configuration = new Configuration()
    Server.configure(configuration)
    Server.startServer(configuration.bindHost, configuration.bindPort)
  }


}


