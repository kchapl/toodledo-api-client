/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

import dispatch._
import dispatch.Defaults._
import scala.concurrent.Await
import scala.concurrent.duration._

trait HttpClient {

  val apiHost = "api.toodledo.com"
  val apiVersion = "2"

  def makeGetRequest(path: List[String],
                     query: Map[String, String] = Map(),
                     secure: Boolean = false): String
}


class DispatchHttpClient extends HttpClient {

  def makeGetRequest(path: List[String],
                     query: Map[String, String] = Map(),
                     secure: Boolean = false): String = {

    val request = ((apiVersion :: path).foldLeft((host(apiHost), secure) match {
      case (host, true) => host.secure
      case (host, false) => host
    })((acc, item) => acc / item)) <<? query

    Await.result(for (responseBody <- Http(request OK as.String)) yield responseBody, 30 seconds)
  }

}
