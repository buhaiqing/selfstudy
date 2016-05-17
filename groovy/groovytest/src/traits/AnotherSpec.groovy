package traits

import groovy.transform.CompileStatic
import groovy.util.logging.Log
import groovy.util.logging.Log4j
import spock.lang.FailsWith
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Timeout
import spock.lang.Title

import java.util.concurrent.atomic.AtomicInteger

import static com.google.common.base.Preconditions.checkNotNull
import static groovyx.gpars.GParsPool.withPool

/**
 * Created by buha on 11/26/2015.
 */
// slidedeck: http://slides.com/mzielinski/spock#/
@Log
@Title("a natural-language name to a spec.")
class AnotherSpec extends Specification implements XFeature3, XFeature1, XFeature1.XNestedFeature2 {

    String name = "welcome to hpe"

    def allmytest() {
//           laziness
        test1()
        test2()
        test2_1()
        test3()
        test4()
        test5()
        expect:
        1 == 1
    }

    // As a PCOE developer, i want to add time trace on functional case.
    // also need to run cases in parallel
    def test_pcoe_load() {
        setup:
        double default_val = 2.0
        def functors = []
        (1..10).each { functors << { test1() } }

        expect:
        // load testing
        [20, 10, 5].each { count ->
            println("count is ${count}")
            withPool(count) {
//                (0..count).collectParallel {
                functors.collectParallel { fun ->
                    def lStart = (double) System.nanoTime()
                    fun()
                    double seconds = (((double) System.nanoTime() - lStart) / 1000000000.0)
//                    println "[PCOE Test case]cost ${seconds} seconds"
                    expect:
                    Double.compare(default_val + 1.0, seconds) >= 0
                    Double.compare(default_val - 1.0, seconds) <= 0
                }
            }
        }
    }

    def test_pcoe_throughput() {
        given: "given .."
        def lStart = System.currentTimeMillis()
        AtomicInteger count = new AtomicInteger(0)

        when: "when..."
        withPool(10) {
            (1..10).collectParallel {
                while (System.currentTimeMillis() - lStart < 1000 * 60) {
                    test1()
                    count.addAndGet(1)
                }
            }
        }
        println("count is ${count}")
        then: 'then ...'
        count.get() > 100

    }

    @CompileStatic
    def run1() {
        def default_val = 2.0
        def lStart = (double) System.nanoTime()
        test1()
        double seconds = (((double) System.nanoTime() - lStart) / 1000000000.0)

        return Double.compare(default_val + 1.0, seconds) >= 0 && Double.compare(default_val - 1.0, seconds) <= 0
    }

    @CompileStatic
    def run2() {
        println "hello"
        true
    }

    def test_map_reduce() {
        def functors = []
        withPool(10) {
            (1..5).toList().collectParallel { functors << { run1() } }
            (1..10).toList().collectParallel { functors << { run2() } }
        }

        when:

        def result = withPool {
            functors.collectParallel { fun -> fun() } everyParallel { it }
        }

        then:
        result
    }

    def test_map_reduce1() {
        withPool {
            this.metaClass.methods.findAllParallel({
                it.name =~ /test.*/
            }).take(4).eachParallel { fun ->
                println "function name: ${fun.name}"
                fun.invoke(this)
            }
        }
        expect:
        1 == 1
    }


    def test_all_values() {
//  QUESTION: how to do pairwise(all-pairs testing -- https://github.com/tallshort/PairwiseTestingLib/blob/master/src/pairwisetesting/test/TestPairwiseTesting.java
//      http://mse.isri.cmu.edu/software-engineering/documents/faculty-publications/miranda/kuhnintroductioncombinatorialtesting.pdf
        println("=====================")
        expect:
        // simulate test function
        println("${x},${y},${z}")
        where:
//        [x, y, z] << GroovyCollections.combinations([['a', 'b', 'c'], [1, 2], [3, 4]])
        [x, y, z] << [['a', 'b', 'c'], [1, 2], [3, 4]].combinations()
    }

    def test_zip_values() {
        expect:
        // simulate test function
        println("${x},${y},${z}")
        where:
        [x, y, z] << [['a', 'b'], [1, 2], [3, 4]].transpose()
    }

//=================AOP====================
    // AOP with trait
    // https://en.wikipedia.org/wiki/Trait_%28computer_programming%29
    // link: http://groovy-lang.org/objectorientation.html#_traits
    def test_trait() {
// as keyword only one element -- as后面只能跟一个Trait 对象
        def obj = new HelloWorld() as MixedTrait
        obj.start_counter()
        obj.print_start()
        obj.sayHello()
        obj.print_end()
        obj.end_counter()
        expect:
        1 == 1
    }

    trait MixedTrait implements HelloWordTrait, PerformanceCounterTrait {}

    // AOP with Groovy Category Feature
    def test_groovy_category() {
        use(HelloWorldOps) {
            def obj = new HelloWorld()
            obj.print_start()
            obj.sayHello()
            obj.print_end()
        }

        expect:
        1 == 1
    }

    // Meta Object Protocol
    def test_mop() {
        setup:
        // alias method in Ruby
        def savedMethodd = HelloWorld.metaClass.getMetaMethod('sayHello')
        checkNotNull(savedMethodd)
        HelloWorld.metaClass.sayHello = {
            println("start to run")
            savedMethodd.invoke(delegate)
            println("end to run")

        }

        def hello = new HelloWorld()
        hello.sayHello()

        def savedStaticMethodd = HelloWorld.metaClass.getStaticMetaMethod('sayWelcome')
        checkNotNull(savedStaticMethodd)
        HelloWorld.metaClass.static.sayWelcome = {
            println("start to run static method")
            savedStaticMethodd.invoke(delegate)
            println("end to run static method")
        }
        HelloWorld.sayWelcome()
        checkNotNull HelloWorld.metaClass.getMetaMethod('private_method')

        expect:
        1 == 1
    }


    class HelloWorldDelegate {
        @Delegate
        HelloWorld world = new HelloWorld()

        def start_counter() {
            println("start counter")
        }

        def end_counter() {
            println("end counter")
        }

    }

    def test_delegate() {
        def delegate = new HelloWorldDelegate()
        delegate.start_counter()
        delegate.sayHello()
        delegate.end_counter()

        expect:
        1 == 1
    }

}


trait XFeature1 {
    String name = "Hello world"


    def test4() {
        expect:   "as"
        println "test4"
    }

    def test5() {

        expect:
        println "test5"

    }

    def test2() {
        expect:
        System.out.println(this.name + " " + "Feature2");
        1 == 1
    }

//    ================Nested ========================
    static trait XNestedFeature2 {
        String name = "say goodbye"


        def test2_1() {
            expect:
            println this.name + " " + "NestedFeature2"
        }
    }
}

trait XFeature2 {

    def test1() {
        expect:
        System.out.println(this.name + " in " + "Feature2");
    }
}

trait XFeature3 extends XFeature2 {

    def test3() {
        expect:
        System.out.println(this.name + " " + "Feature3");
    }

    def test1() {
//        println("test1 in XFeature3")
        sleep(2 * 1000)
//        super.test1()
                          super.test1()
    }


}

