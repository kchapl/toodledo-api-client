/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

import org.specs2.mutable._


class ContextsApiTest extends Specification {

  "The ContextsApi" should {
    "fetch Context instances" in {
      val httpClient = new StubHttpClient
      val api = new ContextsApi(null, httpClient)

      val contexts = api.fetch

      contexts must contain(Context(1, "@ca"), Context(2, "@cb"), Context(3, "@cc")).inOrder
    }
  }
}
