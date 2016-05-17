import java.beans.BeanInfo
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.BiPredicate
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream

import static java.util.List.*

/**
 * Created by buha on 9/18/2015.
 */

class TestData implements Callable<String> {
    private String param;

    public TestData(String data) {
        this.param = data
    }

    @Override
    String call() throws Exception {
        StringBuffer buffer = new StringBuffer()
        0.upto(1000) { buffer.append(param) }
        return buffer.toString()
    }
}

class helloworld {

    def o = new Timer("test")

    public void test() {
        final AtomicBoolean flag = new AtomicBoolean(true)
        o.schedule(new TimerTask() {


            @Override
            void run() {
                if (flag.get()) {
                    println("hello world")
                    flag.set(false)
                } else {
                    println("end")
                    cancel()
                }
            }
        }, 0, 1)
    }

    public static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public int partition(int[] data, int l, int r, int pivot) {
        //        do {
        //        while (data[++l] < pivot);
        //        while ((r != 0) && data[--r] > pivot);
        //        swap(data, l, r);
        //    }
        while (l < r) {
            while (data[++l] < pivot) {
            }
            while ((r != 0) && data[--r] > pivot) {
            }

            swap(data, l, r)
        }
        swap(data, l, r)
        return l;
    }

    private static final class Thread2 extends Thread {
        public Thread2(String name) {
            super(name);
        }

        public void run() {
            try {
                for (int i = 1; i <= 1000; i++) {
                    System.out.println("-- thread" + this.getName());
                    if (i % 100 == 0) {
                        Thread.yield()
                    };
                }
            }
            catch (Exception e) {
                System.out.println("Ex: " + e.getMessage());
            }
        }
    }

    def test(number) {
        def func = { (1..number).sum() }.memoize()
        func()

    }

    public static final void main(String[] args) {
        def of = IntStream.of(1, 2, 3, 4, 5)
        def collect = of.map({ i -> i * i })
        collect.forEach({s->println s})


//        java.util.List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
//        numbers.stream().distinct().forEach({s->println s});
//        def o = [1..10].flatten()
//        println o.size()
//        def xx = [o, o[1..-2]].transpose()
//        println xx

        //        def arr = ['a', 'b', 'c', 'd', 1, 2, 3, 4].asList()
//        arr.each { print it }
//        println "\r\n============"
//        Collections.rotate(arr, 4)
//        arr.each { print it }
//        //        def obj = new helloworld()
        //        obj.test()

        //        def pool = Executors.newFixedThreadPool(1)
        //        FutureTask<String> data = new FutureTask<String>(new TestData("a"))
        //        pool.execute(data)
        //        println "done"
        //        println data.get()
        //        println ("exit")

        //        int[] arr = (1..11).toArray()
        //        def result = findMinMax(arr, 0, arr.length-1)
        //        println "min value is ${result.get(0)}"
        //        println "max value is ${result.get(1)}"

    }

    static int findMax(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }

        if (right - 1 == left) {
            return arr[left] > arr[right] ? arr[left] : arr[right];
        }

        int middle = (left + right) / 2;
        int max1 = findMax(arr, left, middle);
        int max2 = findMax(arr, middle + 1, right);
        return max1 > max2 ? max1 : max2;
    }


    static Tuple findMinMax(int[] arr, int left, int right) {
        if (left == right) {
            return new Tuple(arr[left], arr[left]);
        }

        if (right - 1 == left) {
            int max_value = arr[left] > arr[right] ? arr[left] : arr[right]
            int min_value = arr[left] < arr[right] ? arr[left] : arr[right]
            return new Tuple(min_value, max_value);
        }

        int middle = (left + right) / 2;
        Tuple tuple1 = findMinMax(arr, left, middle);
        Tuple tuple2 = findMinMax(arr, middle + 1, right);
        int min1 = tuple1.get(0)
        int max1 = tuple1.get(1)
        int min2 = tuple2.get(0)
        int max2 = tuple2.get(1)
        int max_value = max1 > max2 ? max1 : max2
        int min_value = min1 < min2 ? min1 : min2
        return new Tuple(min_value, max_value)
    }

}
