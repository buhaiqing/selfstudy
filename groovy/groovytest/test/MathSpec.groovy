import spock.lang.Specification

import java.util.function.Predicate

/**
 * Created by buha on 11/18/2015.
 */
public class TestClass {
    List<Integer> findAll() {
        return new ArrayList<Integer>();
    }

    Integer getSize() {
        return this.findAll()?.size();
    }
}

class MathSpec extends Specification {

    def setup() {
        TestClass.metaClass.invokeMethod = { name, args ->
            def m = TestClass.metaClass.getMetaMethod(name, args)
            def l = System.currentTimeMillis()
            def result = m.invoke(delegate, args)
            def duration = System.currentTimeMillis() - l
            println("time duration is ${duration}")

        }
    }

    def test1() {

        expect:
        int sum = 0;
        for (int i = 10; i <= 100; i++) {
            if (i % 3 == 0 || i % 5 == 0) sum += i;
        }
        System.out.println(sum);
    }

    def test2() {
        expect:
        String message = "he saw a racecar";
        println(message.reverse())
    }

    def test3() {
        when:
        TestClass o = new TestClass()

        and:
        def size = o.getSize()
        then:
        size == 0
    }


}