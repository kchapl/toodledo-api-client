package kchapl.toodledo

import org.specs2.mutable._


class ContextsApiTest extends Specification {

  "A ContextsApi" should {
    "fetch Context instances" in {
      val httpClient = new StubHttpClient
      val api = new ContextsApi(null, httpClient)

      val contexts = api.fetch

      contexts must contain(Context(1, "@ca"), Context(2, "@cb"), Context(3, "@cc")).inOrder
    }
  }
}
