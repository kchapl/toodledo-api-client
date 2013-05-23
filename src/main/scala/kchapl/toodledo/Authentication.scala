/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

import net.liftweb.json.JsonAST.{JString, JField, JObject}
import net.liftweb.json.parse
import Digest.md5


object Authentication {

  def lookUpUser(app: App, email: String, password: String, httpClient: HttpClient = Registry.httpClient): User = {
    val sig = md5(email + app.token)
    val responseBody = httpClient.makeGetRequest(List("account", "lookup.php"),
      Map("appid" -> app.id, "sig" -> sig, "email" -> email, "pass" -> password),
      secure = true)

    val JObject(List(JField("userid", JString(id)))) = parse(responseBody)
    User(id, password)
  }

  def key(app: App, user: User, tokenCache: TokenCache) = {
    md5(md5(user.password) + app.token + tokenCache.getToken)
  }
}

case class App(id: String, token: String)

case class User(id: String, password: String)
