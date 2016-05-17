package traits

import org.testng.Assert
import org.testng.annotations.Test
import spock.lang.Specification


/**
 * Created by buha on 12/27/2015.
 */
class TestNgTraits implements trait3, trait4 {
    //  the test cases will be executed at once. no laziness

}

trait trait3 {
    @Test
    public void test1() {
        Assert.assertEquals(1 + 1, 2)
    }

}

trait trait4 {
    @Test
    void test2() {
        Assert.assertEquals(2 * 3, 6)
    }
}
