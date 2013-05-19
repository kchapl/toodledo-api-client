package kchapl.toodledo

           // TODO copyright
class Authentication(app: App, user: User, tokenCache: TokenCache) {

  lazy val key = Digest.md5(Digest.md5(user.password) + app.token + tokenCache.getToken)

  // TODO account lookup
}

case class App(id: String, token: String)

case class User(id: String, password: String)
