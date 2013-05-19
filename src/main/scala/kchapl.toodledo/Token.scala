package kchapl.toodledo

import scalax.file.Path
import org.joda.time.DateTime
import scala.language.postfixOps


class TokenAndExpiryTime(val token: String, expires: DateTime) {
  def isActive = expires.isAfterNow
}

trait TokenCache {

  val app: App
  val user: User
  val httpClient: HttpClient

  def currentTokenAndExpiryTime: Option[TokenAndExpiryTime]

  def generateNewToken: String = {
    val sig = Digest.md5(user.id + app.token)
    val responseBody = httpClient.makeGetRequest(List("account", "token.php"),
      Map("userid" -> user.id, "appid" -> app.id, "sig" -> sig),
      secure = true)
    //TODO use json parser
    val token = responseBody.replaceFirst( """\{"token":"(\w+)"\}""", "$1")
    storeToken(token)
    token
  }

  def storeToken(token: String)

  def getToken: String = {
    currentTokenAndExpiryTime match {
      case Some(current) if (current.isActive) => current.token
      case _ => generateNewToken
    }
  }
}

case class FileSysTokenCache(app: App, user: User, httpClient: HttpClient = Registry.httpClient) extends TokenCache {
  val tokenStore = Path.fromString(sys.env("TMP")) / "td.token.txt"

  def currentTokenAndExpiryTime: Option[TokenAndExpiryTime] = {
    val serialized = tokenStore.string
    if (serialized.isEmpty) None
    else {
      val parts = serialized.split(" until ")
      Some(new TokenAndExpiryTime(parts(0), new DateTime(parts(1).toLong)))
    }
  }

  def storeToken(token: String) {
    tokenStore.write("%s until %s".format(token, new DateTime().plusHours(3).getMillis))
  }

}
