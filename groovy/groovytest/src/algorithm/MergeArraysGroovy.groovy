package algorithm

/**
 * Created by buha on 9/19/2015.
 */
class MergeArraysGroovy
{
    public static void main(String[] args)
    {
        def arr = (10..1).toList();
        Collections.rotate(arr,2)
        for (int i : arr)
        {
            System.out.println(i)
        }
    }
}
