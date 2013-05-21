/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo.client

import scalax.file.Path
import kchapl.toodledo._
import scala.App
import kchapl.toodledo.FileSysTokenCache


object Client extends App {

  val credentialsPath = Path.fromString(sys.env("HOME")) / "Desktop"
  val appId = (credentialsPath / "td.appId.txt").string
  val appToken = (credentialsPath / "td.appToken.txt").string
  val userEmail = (credentialsPath / "td.userEmail.txt").string
  val userPassword = (credentialsPath / "td.userPwd.txt").string

  val app = App(appId, appToken)
  val user = Authentication.lookUpUser(app, userEmail, userPassword)
  val tokenCache = new FileSysTokenCache(app, user)
  def key = Authentication.key(app, user, tokenCache)
  println(key)

  val contextsApi = new ContextsApi(key)
  val contexts = contextsApi.fetch
  println(contexts)

  val tasksApi = new TasksApi(key)
  val tasks = tasksApi.fetch
  println(tasks)
  val deleted = tasksApi.fetchDeleted
  println(deleted)

  val boards = tasks groupBy (_.contextId)
  boards foreach {
    case (key, values) => {
      println("*********************************************************************** " + key)
      val (todo, done) = values partition (_.completed.isEqual(0))
      println("++++++++++++ to do")
      todo foreach (println)
      println("++++++++++++ done")
      done foreach (println)
    }
  }

}


