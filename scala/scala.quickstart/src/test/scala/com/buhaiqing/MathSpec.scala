package com.buhaiqing

import org.scalatest._


/**
 * Created by buha on 11/24/2015.
 */
class MathSpec extends FunSpec with Matchers {
  describe("a test") {
    it("math add",Tag("math")) {
      val (a, b) = (1, 2)
      (a + b) should be(3)
    }
    it("math addex",Tag("math1")) {
      val (a, b) = (1, 2)
      (a + b) should be(3)
    }
  }
  //  "math.add" should "add one case" in {
  //    val (a, b) = (1, 2)
  //    (a + b) should equal(3)
  //  }


}
