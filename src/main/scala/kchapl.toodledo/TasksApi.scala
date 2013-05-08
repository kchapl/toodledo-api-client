package kchapl.toodledo

import dispatch._
import dispatch.Defaults._
import net.liftweb.json._

class TasksApi(key: => String) {

  def fetch: List[Task] = {
    val x = for (responseText <- Http({
      val serviceHost = host("api.toodledo.com") / "2"
      serviceHost / "tasks" / "get.php" <<? Map("key" -> key, "comp" -> "0", "fields" -> "context")
    } OK as.String)) yield responseText

    val json = parse(x())
    val y = for {
      JObject(o) <- json
      JField("id", JString(id)) <- o
      JField("title", JString(title)) <- o
      JField("modified", JInt(modified)) <- o
      JField("completed", JInt(completed)) <- o
      JField("context", JString(context)) <- o
    } yield Task(id.toLong, title, modified.toLong, completed.toLong, context.toLong)

    y
  }

  def fetchDeleted: List[Task] = {
    val x = for (responseText <- Http({
      val serviceHost = host("api.toodledo.com") / "2"
      serviceHost / "tasks" / "deleted.php" <<? Map("key" -> key)
    } OK as.String)) yield responseText

    val json = parse(x())
    val y: List[Long] = for {
      JObject(o) <- json
      JField("id", JString(id)) <- o
    } yield id.toLong

    fetch filter (task => (y.contains(task.id)))
  }

}

// TODO: joda date fields
case class Task(id: Long, title: String, modified: Long, completed: Long, contextId: Long)
