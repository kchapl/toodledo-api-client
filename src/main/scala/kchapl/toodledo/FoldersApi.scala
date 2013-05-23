/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

import net.liftweb.json._


object FoldersApi {

  def fetch(key: => String, httpClient: HttpClient = Registry.httpClient): List[Folder] = {
    val responseBody = httpClient.makeGetRequest(List("folders", "get.php"), Map("key" -> key))

    for {
      JObject(o) <- parse(responseBody)
      JField("id", JString(id)) <- o
      JField("name", JString(name)) <- o
      JField("private", JString(prv)) <- o
      JField("archived", JString(archived)) <- o
      JField("ord", JString(ord)) <- o
    } yield Folder(id.toLong, name, prv.toInt == 1, archived.toInt == 1, ord.toInt)
  }
}

case class Folder(id: Long, name: String, prv: Boolean, archived: Boolean, ord: Int)
