package algorithm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by buha on 9/19/2015.
 */
public class LinearSearch
{
  public void runTest()
  {

  }

  //  public int LinearSearch(int x, int[] arr)
  //  {
  //    int length = arr.length;
  //    for (int i = 0; i < length; i++)
  //    {
  //      if (arr[i] == x)
  //      {
  //        return i;
  //      }
  //    }
  //    return -1;
  //  }
  public int LinearSearch(int x, int[] arr)
  {
    int index = 0;
    int length = arr.length;
    int result = -1;
    while (index < length)
    {
      if (arr[index] == x)
      {
        result = index;
        break;
      }
      index++;
    }

    return result;
  }

  @Test
  public void test1()
  {
    Assert.assertEquals(-1, LinearSearch(1, new int[]{ 11, 2, 3 }));
  }

  @Test
  public void test2()
  {

    Assert.assertEquals(0, LinearSearch(1, new int[]{ 1, 2, 3 }));
  }


}
