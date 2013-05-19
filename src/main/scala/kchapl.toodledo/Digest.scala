package kchapl.toodledo

import java.security.MessageDigest

object Digest {
  private val md5Digest = MessageDigest.getInstance("MD5")

  def md5(s: String) = md5Digest.digest(s.getBytes).map("%02x".format(_)).mkString
}
