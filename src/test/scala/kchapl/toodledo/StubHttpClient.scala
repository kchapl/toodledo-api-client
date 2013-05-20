package kchapl.toodledo

class StubHttpClient extends HttpClient {

  def makeGetRequest(path: List[String], query: Map[String, String], secure: Boolean): String = path match {
    case List("contexts", "get.php") =>
      """[{"id":"1","name":"@ca"},{"id":"2","name":"@cb"},{"id":"3","name":"@cc"}]"""
    case _ => throw new IllegalArgumentException
  }
}
