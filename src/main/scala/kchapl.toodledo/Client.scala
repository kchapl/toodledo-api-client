package kchapl.toodledo

import scalax.file.Path


object Client extends App {

  val credentialsPath = Path.fromString(sys.env("HOME")) / "Desktop"
  val userId = (credentialsPath / "td.userId.txt").string
  val userPassword = (credentialsPath / "td.userPwd.txt").string
  val appId = (credentialsPath / "td.appId.txt").string
  val appToken = (credentialsPath / "td.appToken.txt").string

  val tokenCache = new FileSysTokenCache(userId, appId, appToken)
  val auth = new Authentication(userPassword, appToken, tokenCache)
  val key = auth.key
  println(key)

  val contextsApi = new ContextsApi(key)
  val contexts = contextsApi.fetch
  println(contexts)

  val tasksApi = new TasksApi(key)
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