import spock.lang.Specification
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo


class Publisher
{
    List<Subscriber> subscribers = new ArrayList<>()

    void send(String message)
    {
        subscribers?.each { it.receive(message) }
    }


    void test(int param)
    {
        subscribers.each { it.test(param) }
    }
}

interface Subscriber
{
    void receive(String message)

    void test(int p)
}

class MySubcriber implements Subscriber
{
    @Override
    void receive(final String message)
    {

    }

    void test(int p)
    {
        p == 0 ? test1() : test2()
    }

    def test1()
    {
        println "test1"
    }

    def test2()
    {
        println "test2"
    }
}

/**
 * Created by buha on 10/29/2015.
 */
class PublisherSpec extends Specification
{
    MySubcriber subscriber1 = Mock()
    Subscriber subscriber2 = Mock()
    def publisher = new Publisher()

    def setup()
    {
        publisher.subscribers.add(subscriber1)
        publisher.subscribers.add(subscriber2)
    }

    @Unroll
    def "test mock"()
    {
        when:
        publisher.send("hello")
        then:
        1 * subscriber1.receive("hello")
        assert 1+1==2

    }

    def "test test method"()
    {
        when:
        publisher.test(0)
        then:
        1 * subscriber1.test(0)
    }
    def "comparing two decimal numbers"() {
        def myPi = 3.14

        expect:
        myPi  closeTo(Math.PI, 0.01)
    }
}


