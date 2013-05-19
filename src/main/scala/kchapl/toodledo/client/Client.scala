package kchapl.toodledo.client

import scalax.file.Path
import kchapl.toodledo._
import scala.App
import kchapl.toodledo.FileSysTokenCache


object Client extends App {

  //TODO compile with deprecation
  //TODO compile with feature warnings
  val credentialsPath = Path.fromString(sys.env("HOME")) / "Desktop"
  val userId = (credentialsPath / "td.userId.txt").string
  val userPassword = (credentialsPath / "td.userPwd.txt").string
  val appId = (credentialsPath / "td.appId.txt").string
  val appToken = (credentialsPath / "td.appToken.txt").string

  val app = App(appId, appToken)
  val user = User(userId, userPassword)
  val tokenCache = new FileSysTokenCache(app, user)
  val auth = new Authentication(app, user, tokenCache)
  println(auth.key)

  val contextsApi = new ContextsApi(auth.key)
  val contexts = contextsApi.fetch
  println(contexts)

  // TODO: ensure fetched by name not value
  val tasksApi = new TasksApi(auth.key)
  val tasks = tasksApi.fetch
  println(tasks)
  // TODO: fix this
  val deleted = tasksApi.fetchDeleted
  println(deleted)
  // TODO: fetch uncompleted tasks by context

  val boards = tasks groupBy (_.contextId)
  boards foreach {
    case (key, values) => {
      println("*********************************************************************** " + key)
      val (todo, done) = values partition (_.completed == 0)
      println("++++++++++++ to do")
      todo foreach (println)
      println("++++++++++++ done")
      done foreach (println)
    }
  }

}


