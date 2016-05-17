package algorithm

import org.junit.Assert
import org.junit.Test

/**
 * Created by buha on 9/20/2015.
 */
class SelectionSort
{
    public void seleectionSort(int[] arr, i)
    {
        int n = arr.length;
        if (i >= n || n == 0 || n == 1)
        {
            return
        }

        int k = i;

        for (int j = i + 1; j < n; j++)
        {
            if (arr[j] < arr[k])
            {
                k = j
            }
        }
        if (i != k)
        {
            SortUtil.swap(arr,i,k )
        }
        seleectionSort(arr, i + 1);
    }

    @Test
    public void test1()
    {
        int[] arr = [3, 2, 1]
        seleectionSort(arr, 0)

        Assert.assertArrayEquals([1, 2, 3].toArray(), arr);
    }

    @Test
    public void test2()
    {
        int[] arr = []
        seleectionSort(arr, 0)
        Assert.assertArrayEquals([].toArray(), arr);
    }

    @Test
    public void test3()
    {
        int[] arr = [11]
        seleectionSort(arr, 0)
        Assert.assertArrayEquals([11].toArray(), arr);
    }
}
