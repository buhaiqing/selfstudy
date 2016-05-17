package algorithm;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by buha on 9/19/2015. it aims to merge to arrays into one sorted one
 */
public class MergeArrays
{
  @Test
  public void test1()
  {
    int[] arr1 = { 1, 5, 4, 3 };
    int[] arr2 = { 2, 9 };
    Assert.assertArrayEquals(new int[]{ 1, 2, 3, 4, 5, 9 }, merge(arr1, arr2));
  }


  public static int[] InsertSort(int[] arr)
  {
    int i, j;
    int insertNote;
    int[] array = arr;

    for (i = 1; i < array.length; i++)
    {
      insertNote = array[i];
      j = i - 1;
      while (j >= 0 && insertNote < array[j])
      {
        j--;
      }
      array[j + 1] = insertNote;
    }
    System.out.println(Arrays.toString(array));
    return array;

  }

  private int[] merge(final int[] arr1, final int[] arr2)
  {
    Arrays.sort(arr1);
    Arrays.sort(arr2);

    int[] result = new int[arr1.length + arr2.length];
    int i, j, k;
    i = j = k = 0;
    int arr1_len = arr1.length;
    int arr2_len = arr2.length;

    while (i < arr1_len && j < arr2_len)
    {
      if (arr1[i] < arr2[j])
      {
        result[k] = arr1[i];
        i++;
      }
      else
      {
        result[k] = arr2[j];
        j++;
      }
      k++;
    }

    if (i == arr1_len)
    {

      System.arraycopy(arr2, j, result, k, arr2_len - j);
    }
    else
    {
      System.arraycopy(arr1, i, result, k, arr1_len - i);
    }

    for (int g : result)
    {
      System.out.println(g);
    }
    return result;
  }

  public static void main(String[] args)
  {
    int[] arr = { 1, 26, 7, 2, 3, 8, 5 };
    Arrays.sort(arr, 2, arr.length );
    for (int i : arr)
    {
      System.out.println(i);
    }
    Thread.yield();
  }

}
