/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

import net.liftweb.json._

class ContextsApi(key: => String, httpClient: HttpClient = Registry.httpClient) {

  def fetch: List[Context] = {
    val responseBody = httpClient.makeGetRequest(List("contexts", "get.php"), Map("key" -> key))

    for {
      JObject(o) <- parse(responseBody)
      JField("id", JString(id)) <- o
      JField("name", JString(name)) <- o
    } yield Context(id.toLong, name)
  }
}

case class Context(id: Long, name: String)
