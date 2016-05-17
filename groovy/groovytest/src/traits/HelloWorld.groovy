package traits

import groovy.transform.CompileStatic

/**
 * Created by buha on 12/2/2015.
 */
//class HelloWorld {
//    def sayHello() {
//        println("Hello World")
//    }
//}

@CompileStatic
@Category(HelloWorld)
class HelloWorldOps {
    // should be static
    static String test_case = "sample test case"

    def print_start() {
        println("start to run --  ${test_case}")
    }

    def print_end() {
        println("end to run --  ${test_case}")
    }
}

trait HelloWordTrait {
    String test_case = "sample test case"

    def print_start() {
        println("start to run --  ${test_case}")
    }

    def print_end() {
        println("end to run --  ${test_case}")
    }
}
 trait PerformanceCounterTrait{
     def start_counter(){
         println("start counter")
     }
     def end_counter(){
         println("end counter")
     }
 }



