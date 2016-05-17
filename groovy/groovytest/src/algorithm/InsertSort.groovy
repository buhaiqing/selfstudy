package algorithm

import org.junit.Assert
import org.junit.Test

/**
 * Created by buha on 9/20/2015.
 */
class InsertSort
{

    // not recurse
    public void insertSort(int[] data)
    {
        for (int i = 1; i < data.length; i++)
        {
            for (int j = i; (j > 0) && (data[j] < data[j - 1]); j--)
            {
                SortUtil.swap(data, j, j - 1)
            }
        }
    }

    //recurse
//    public void insertSort(int[] data){
//
//        Arrays.merge
//    }

    @Test
    public void test1()
    {
        int[] arr = [3, 5, 4, 2, 1]
        insertSort(arr)

        Assert.assertArrayEquals([1, 2, 3, 4, 5].toArray(), arr);
    }

    @Test
    public void test2()
    {
        int[] arr = []
        insertSort(arr)
        Assert.assertArrayEquals([].toArray(), arr);
    }

    @Test
    public void test3()
    {
        int[] arr = [11]
        insertSort(arr)
        Assert.assertArrayEquals([11].toArray(), arr);
    }
}
