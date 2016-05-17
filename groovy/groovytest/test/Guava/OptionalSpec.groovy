package Guava

import com.google.common.base.Preconditions
import com.google.common.collect.Ordering
import spock.lang.Specification


/**
 * Created by buha on 11/9/2015.
 */
class OptionalSpec extends Specification
{
    def "test1"()
    {
        when:
        Optional<Integer> opts = Optional.of(5)
        then:
        opts.isPresent()
        opts.get() == 5
        Preconditions.checkNotNull(opts.get())

    }

    def test2()
    {
        given:
        def order = Ordering.natural()
        def olist = new ArrayList()
        olist.with {
            add 1
            add 2
            add 3
        }
        when:
        def result = order.greatestOf(olist, 2)
        then:
        result.size() == 2
        result.contains(2)
        result.contains(3)
    }
}