package traits;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;

/**
 * Created by buha on 12/6/2015.
 */
public class MyTestNgTest {
    long id;

    public MyTestNgTest() {
        id = Thread.currentThread().getId();
    }

    @DataProvider(name = "test1")
    public Object[][] createData1() {
        return new Object[][]{
                {"Cedric", new Integer(36)},
                {"Anne", new Integer(37)},
                {"Anne1", new Integer(11)},
                {"Anne2", new Integer(21)},
        };
    }

    //This test method declares that its data should be supplied by the Data Provider
//named "test1"
    @Test(dataProvider = "test1", threadPoolSize = 3, invocationCount = 10, timeOut = 10000)
    public void verifyData1(String n1, Integer n2) {
//        System.out.println(n1 + " " + n2);
        System.out.println(Thread.currentThread().getId());
    }


}


