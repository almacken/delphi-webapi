package de.upb.cs.swt.delphi.webapi

import akka.http.scaladsl.server.HttpApp
import de.upb.cs.swt.delphi.featuredefinitions.FeatureListMapping
import spray.json._

/**
  * Web server configuration for Delphi web API.
  */
object Server extends HttpApp with JsonSupport {

   override def routes =
      path("version") { version } ~
        path("features") { features } ~
        pathPrefix("search" / Remaining) { query => search(query) } ~
        pathPrefix("retrieve" / Remaining) { identifier => retrieve(identifier) }


  private def version = {
    get {
      complete {
        BuildInfo.version
      }
    }
  }

  private def features = {
    get {
      complete {
        FeatureListMapping.featureList.toJson
      }
    }
  }

  def retrieve(identifier: String) = {
    get {
      complete(
        ElasticClient.getSource(identifier)
      )
    }
  }

  def search(query: String) = {
    get {
      complete {
        query
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val configuration = new Configuration()
    Server.startServer(configuration.bindHost, configuration.bindPort)
  }


}


