package de.upb.cs.swt.delphi.webapi

import com.sksamuel.elastic4s.http.HttpClient

/**
  * @author Ben Hermann
  */
trait SearchFeature {

  def search(query : String)(implicit configuration: Configuration) : Seq[String] = {
    val client = HttpClient(configuration.elasticsearchClientUri)

    /*client.execute {

    }*/

    Seq("Search executed")
  }

}
