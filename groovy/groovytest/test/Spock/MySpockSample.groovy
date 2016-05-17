package Spock

import spock.lang.Specification

class ITest {

    String test(IMock mo) {
        return mo.test1()
    }
}

interface IMock {
    String test1()
}

class Person {
    String name
    def sayHello(){
        return "hello"
    }
}

/**
 * Created by buha on 3/20/2016.
 */
class MySpockSample extends Specification {
    def "test for ITest"() {
        setup:
        ITest p = new ITest()
        IMock stub = Mock()
        stub.test1() >> "good"
        when:
        def result = p.test(stub)
//        when:
//        stub.test1() >> "good"
        then:
        result == "good"
    }

    def "test person"() {
        def spy = Spy(Person,constructorArgs:[])
        when:
        spy.sayHello() >> "hello world"
        def result = spy.sayHello()
        then:
        result == "hello world"
    }
}