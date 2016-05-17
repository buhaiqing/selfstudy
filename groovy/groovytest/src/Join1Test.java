import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by buha on 9/19/2015.
 */
public class Join1Test
{

  private List<FutureTask<Integer>> olist =
    Collections.synchronizedList(new ArrayList<FutureTask<Integer>>());
  final static Object lock = new Object();

  class TestData<Integer> implements Callable<Integer>
  {
    private Integer param;

    public TestData(Integer i)
    {
      this.param = i;

    }

    @Override
    public Integer call() throws Exception
    {
      //      Thread.sleep(1000);
      System.out.println("[ The Data is " + param + "]");
      return param;
    }
  }

  public void runtest() throws InterruptedException
  {
    final ExecutorService service = Executors.newFixedThreadPool(5);
    for (Integer i = 0; i < 100; i++)
    {
      FutureTask<Integer> task = new FutureTask<Integer>(new TestData<Integer>(i));
      olist.add(task);
      service.execute(task);
    }
    service.shutdown();
    service.awaitTermination(1000, TimeUnit.MILLISECONDS);
    if (service.isShutdown())
    {
      System.out.println("shutdown");
    }

    //    boolean found = false;
    //    while (!found)
    //    {
    //      for (FutureTask<Integer> task : olist)
    //      {
    //        if (task.isDone())
    //        {
    //          found = true;
    //        }
    //
    //      }
    //      if (found)
    //      {
    //        System.out.println("Done");
    //      }
    //    }
  }

  public static void main(String[] args) throws InterruptedException
  {

    HashSet shortSet = new HashSet();
    for (short i = 0; i < 100; i++) {
      shortSet.add(i);
      shortSet.remove(i - 1);
    }
    System.out.println(shortSet.size());

    //    Join1Test join1Test = new Join1Test();
    //    join1Test.runtest();
    //    System.out.println("Done");
  }
}
