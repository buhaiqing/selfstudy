import org.junit.Assert;
import org.junit.Test;

/**
 * Created by buha on 9/19/2015.
 */

public class JoinTest
{
  public static volatile int count = 0;

  /**
   *
   */


  @Test
  public void runtest() throws InterruptedException
  {
    for (int i = 0; i < 100; i++)
    {
      TestData testData = new TestData(i);
      testData.start();
      testData.join();
    }
    System.out.println("done:" + count);
    Assert.assertEquals(count, 100);
  }

  public static void main(String[] args) throws InterruptedException
  {
    JoinTest testJoin = new JoinTest();
    testJoin.runtest();
  }

  class TestData extends Thread
  {
    private final int param;

    public TestData(Integer i)
    {
      this.param = i;
    }

    @Override
    public void run()
    {
      super.run();
      System.out.println("[ The Data is " + param + "]");
      JoinTest.count++;
    }
  }


}