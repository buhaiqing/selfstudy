package scala.com.buhaiqing.chapter1

import com.buhaiqing.chapter1.Employee

import scala.sys.Prop

/**
 * Created by buha on 11/23/2015.
 */
object Hello extends App {
  var number: Int = 10
  number = 110
  println(number)
  DoNothing.redo()
  DoNothing.undo()

  var o = new SampleClass with ExSample with SpecifiedSampmle
  o.beforeclick()
  o.afterClick()

  var d = (1, 2, 3, 4, 5)
  println(d.getClass)

  //====================
  mulitiply(12)(3)
  println(number)

  def mulitiply(x: Int)(y: Int) = {
    number = x * y
  }

  //====================
  var e: Employee with Sample = null
  val x = false
  if (x) {
    e = new Employee with Sample {
      override def beforeclick(): Unit = {}

      override def afterClick(): Unit = {}
    }
  }
  else {
    e = new Employee with ExSample
  }
  e.setName("buhaiqing")
  e.setAge(36)
  e.beforeclick()
  e.afterClick()

  //====================
  println(AddTwo(137,2)) // result is 140

}

object AddTwo extends ((Int, Int) => Int)() {
  def apply(x: Int, y: Int = 2) = x + y + 1
}

abstract class UndoableAction(val description: String) {
  def undo(): Unit

  def redo(): Unit

}

object DoNothing extends UndoableAction("do nonthing") {
  override def undo(): Unit = {}

  override def redo(): Unit = {}
}

trait Sample {
  var number: Int = 0

  def beforeclick(): Unit

  def afterClick(): Unit
}

trait ExSample extends Sample {
  override def beforeclick(): Unit = {
    println("before click in ExSample")
  }

  override def afterClick(): Unit = {
    println("after click in ExSample")
  }
}

trait SpecifiedSampmle extends Sample {
  abstract override def beforeclick(): Unit = {
    println(s"before click ${number}")
    //    number = number + 1
    number += 1
    super.beforeclick()
  }

  abstract override def afterClick(): Unit = {
    println(s"after click ${number}")
    super.afterClick()
  }

}

abstract class SampleClass extends Sample {
  number = 11
}

