/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

import net.liftweb.json.JsonAST.{JObject, JField, JString}
import net.liftweb.json._

object GoalsApi {

  def fetch(key: => String, httpClient: HttpClient = Registry.httpClient): List[Goal] = {
    val responseBody = httpClient.makeGetRequest(List("goals", "get.php"), Map("key" -> key))

    for {
      JObject(o) <- parse(responseBody)
      JField("id", JString(id)) <- o
      JField("name", JString(name)) <- o
      JField("level", JInt(level)) <- o
      JField("archived", JInt(archived)) <- o
      JField("contributes", JInt(contributes)) <- o
      JField("note", JString(note)) <- o
    } yield Goal(id.toLong, name, level.toInt, archived.toInt == 1, contributes.toInt, note)
  }
}

case class Goal(id: Long, name: String, level: Int, archived: Boolean, contributes: Int, note: String)
