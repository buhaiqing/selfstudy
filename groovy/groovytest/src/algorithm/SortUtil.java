package algorithm;

/**
 * Created by buha on 9/20/2015.
 */
public class SortUtil
{
  public static void swap(int[] data, int i, int j)
  {
    int temp = data[i];
    data[i] = data[j];
    data[j] = temp;
  }

}
