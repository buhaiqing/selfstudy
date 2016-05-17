package traits

import groovy.transform.CompileStatic
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by buha on 11/26/2015.
 */
@CompileStatic
class traitsExample {
    String name = "welcome to hpe"

    @Test
    public void test1() {
        System.out.println(this.name);
    }

    @Test
    public void test2() {
        println "test2"
    }

    @Test
    public void test3() {
        println "test3"
    }

    @DataProvider(name = "test1")
    public Object[][] createData1() {
        return [
                ["Cedric", new Integer(36)],
                ["Anne", new Integer(37)],
                ["Anne1", new Integer(11)],
                ["Anne2", new Integer(21)],
        ] as Object[][];
    }

    //This test method declares that its data should be supplied by the Data Provider
//named "test1"
    @Test(dataProvider = "test1", threadPoolSize = 4, timeOut = 10000L)
    public void verifyData1(String n1, Integer n2) {
        System.out.println(n1 + " " + n2);
        assertEquals(n2 > 10, true)
//        System.out.println(Thread.currentThread().getId());
    }


}


