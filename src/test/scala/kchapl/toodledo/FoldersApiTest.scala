/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package kchapl.toodledo

import org.specs2.mutable._


class FoldersApiTest extends Specification {

  "The FoldersApi" should {
    "fetch Folder instances" in {
      val httpClient = new StubHttpClient

      val folders = FoldersApi.fetch(null, httpClient)

      folders must contain(
        Folder(123, "Shopping", prv = false, archived = false, 1),
        Folder(456, "Home Repairs", prv = false, archived = false, 2),
        Folder(789, "Vacation Planning", prv = false, archived = false, 3)
      ).inOrder
    }
  }
}
