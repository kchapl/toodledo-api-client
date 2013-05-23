/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

class StubHttpClient extends HttpClient {

  def makeGetRequest(path: List[String], query: Map[String, String], secure: Boolean): String = path match {
    case List("contexts", "get.php") =>
      """[{"id":"1","name":"@ca"},{"id":"2","name":"@cb"},{"id":"3","name":"@cc"}]"""
    case List("folders", "get.php") =>
      """[{"id":"123","name":"Shopping","private":"0","archived":"0","ord":"1"},{"id":"456","name":"Home Repairs","private":"0","archived":"0","ord":"2"},{"id":"789","name":"Vacation Planning","private":"0","archived":"0","ord":"3"}]"""
    case _ => throw new IllegalArgumentException
  }
}
