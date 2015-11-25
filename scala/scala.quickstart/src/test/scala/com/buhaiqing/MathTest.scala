package scala.com.buhaiqing


import com.buhaiqing.chapter1.Employee
import org.junit.Test
import org.junit.Assert._
import scala.com.buhaiqing.chapter1.Person


/**
 * Created by buha on 11/24/2015.
 */
class MathTest {
  @Test def test2() {
    val e = new Employee()
    e.setName("bhq")
    e.setAge(36)
    assertEquals(e.getName, "bhq")
    assertEquals(e.getAge, 36)
  }

  //  JUnit like test case
  @Test(expected = classOf[ArithmeticException])
  def math_add(): Unit = {
    val (a, b) = (1, 0)
    assertEquals(a / b, 3)
  }

  @Test def test1() {
    val p: Person = new Person("andy", 36)
    assertEquals(p.getName, "andy")
    p.setAge(37)
    assertEquals(p.getAge, 37)
  }


}
