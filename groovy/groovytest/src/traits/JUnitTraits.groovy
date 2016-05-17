package traits

import org.junit.Assert
import org.junit.Test


/**
 * Created by buha on 12/15/2015.
 */
class JUnitTraits implements trait1, trait2 {
//  the test cases will be executed at once. no laziness
}

trait trait1 {
    @Test
    public void test1() {
        Assert.assertEquals(1 + 1, 2)
    }

}

trait trait2 {
    @Test
    void test2() {
        Assert.assertEquals(2 * 3, 6)
    }
}
