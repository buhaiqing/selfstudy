package scala.com.buhaiqing.chapter1

import scala.beans.BeanProperty

/**
 * Created by buha on 11/25/2015.
 */
class Person(@BeanProperty var name: String, @BeanProperty var age: Int) {
  def run(): Unit = {
    def arr = Range(1, 10)
    for (x <- arr.par) {
      println(x * 10)
    }
  }
}
