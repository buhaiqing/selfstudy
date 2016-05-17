package algorithm;

/**
 * Created by buha on 9/20/2015.
 */
public class FindMax
{
  public static void main(String[] args)
  {
    int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    System.out.println(findMax(arr, 0, 9));
  }

  static int findMax(int[] arr, int left, int right)
  {
    if (left == right)
    {
      return arr[left];
    }

    if (right - 1 == left)
    {
      return arr[left] > arr[right] ? arr[left] : arr[right];
    }

    int middle = (left + right) / 2;
    int max1 = findMax(arr, left, middle);
    int max2 = findMax(arr, middle + 1, right);
    return max1 > max2 ? max1 : max2;
  }
}
