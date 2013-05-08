package kchapl.toodledo

import dispatch._
import dispatch.Defaults._
import net.liftweb.json._

class ContextsApi(key: => String) {

  def fetch: List[Context] = {
    val x = for (responseText <- Http({
      val serviceHost = host("api.toodledo.com") / "2"
      serviceHost / "contexts" / "get.php" <<? Map("key" -> key)
    } OK as.String)) yield responseText

    val json = parse(x())
    val y = for {
      JObject(o) <- json
      JField("id", JString(id)) <- o
      JField("name", JString(name)) <- o
    } yield Context(id.toLong, name)

    y

  }

}

case class Context(id: Long, name: String)
