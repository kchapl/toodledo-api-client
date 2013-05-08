package kchapl.toodledo

import dispatch._
import dispatch.Defaults._
import java.security.MessageDigest
import scalax.file.Path
import org.joda.time.DateTime

class Authentication(userPassword: String,
                     appToken: String,
                     tokenCache: TokenCache) {

  lazy val key = Digest.md5(Digest.md5(userPassword) + appToken + tokenCache.getToken)

  // TODO factor out dispatch code

  // TODO account lookup
}

object Digest {
  private val md5Digest = MessageDigest.getInstance("MD5")

  def md5(s: String) = md5Digest.digest(s.getBytes).map("%02x".format(_)).mkString
}

class TokenAndExpiryTime(val token: String, expires: DateTime) {
  def isActive = expires.isAfterNow
}

trait TokenCache {

  def currentTokenAndExpiryTime: Option[TokenAndExpiryTime]

  def generateNewToken: String

  def getToken: String = {
    currentTokenAndExpiryTime match {
      case Some(current) if (current.isActive) => current.token
      case _ => generateNewToken
    }
  }
}

class FileSysTokenCache(userId: String, appId: String, appToken: String) extends TokenCache {
  val tokenStore = Path.fromString(sys.env("TMP")) / "td.token.txt"

  def currentTokenAndExpiryTime: Option[TokenAndExpiryTime] = {
    val serialized = tokenStore.string
    if (serialized.isEmpty) None
    else {
      val parts = serialized.split(" until ")
      Some(new TokenAndExpiryTime(parts(0), new DateTime(parts(1).toLong)))
    }
  }

  def generateNewToken: String = {
    val sig = Digest.md5(userId + appToken)

    val x = for (responseText <- Http({
      host("api.toodledo.com").secure / "2" / "account" / "token.php" <<?
        Map("userid" -> userId, "appid" -> appId, "sig" -> sig)
    } OK as.String)) yield responseText

    val token = x().replaceFirst( """\{"token":"(\w+)"\}""", "$1")
    tokenStore.write("%s until %s".format(token, new DateTime().plusHours(3).getMillis))
    token
  }
}
