import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by buha on 10/21/2015.
 */
class MySampleSpockTest extends Specification
{
    def test_int = 2

    def test1()
    {
        //        when:
        //        def d = 1
        //        setup://
        expect:
        a + b == c
        where:
        a | b | c
        1 | 2 | 3
    }

    @Unroll
    def "maximum of two numbers"()
    {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        3 | 5 || 5
        7 | 0 || 7
        0 | 0 || 0
    }

    def "abs"()
    {
        expect:
        Math.abs(a) == 1
        where:
        a  | _
        1  | _
        -1 | _
    }


    def "#{#test_int/2} is expected"()
    {
      expect:
          a== 1

    }
}

