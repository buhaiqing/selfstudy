package jdk8;

import org.testng.annotations.Test;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.IntStream.of;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by buha on 11/26/2015.
 */
interface II {
    default void sayhello(Integer s, String b, BiPredicate<Integer, String> cond) {
        if (cond.test(s, b)) {
            System.out.println("found");
        } else {
            System.out.println("not found");
        }
    }
}

public class ArraysSample implements II {
    @Test
    public void test1() throws InterruptedException {
       IntStream.rangeClosed(1,5).map(i-> i*i).forEach(System.out::println);


        int[] arr = {1, 2, 4};
        ArrayList<String> oss = new ArrayList<>();
        Optional<String> os = Optional.ofNullable("hello world");
        os.ifPresent(System.out::println);
//        Optional<Boolean> aBoolean = os.map(oss::add);
//        System.out.println(aBoolean);

        IntStream.range(1, 5).map((int x) -> x + 2).forEach(x -> {
            System.out.println(x);
        });
        System.out.println("========================");
//        Arrays.parallelPrefix(arr, new IntBinaryOperator() {
//            @Override
//            public int applyAsInt(int left, int right) {
//                return left + right;
//            }
//        });
        Arrays.parallelPrefix(arr, (x, y) -> x + y);
        for (int i : arr) {
            System.out.println(i);
        }
        System.out.println("========================");
        IntSummaryStatistics collect = Stream.of("a", "b", "c").limit(10).collect(Collectors.summarizingInt(String::length));
        System.out.println(collect);

        ConcurrentHashMap<String, Integer> omap = new ConcurrentHashMap<>();
        String[] split = "hello world".split("\\s");
        Stream.of(split).forEach(System.out::println);

        List<Integer> integers = new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4}));
        integers.removeIf(n -> n % 2 == 0);
        integers.forEach(System.out::println);

        System.out.println("========================");
        HashMap<String, Integer> maps = new HashMap<>();
        maps.put("Java", 11);
//        maps.values().forEach(System.out::println);
        maps.merge("C#", 12, (a, b) -> b);
        maps.values().forEach(System.out::println);

    }

    //    @Test(dependsOnMethods = {"test1"})
    @Test
    public void test2() {
        System.out.println("hello world");
    }

    @Test
    public void test_weakReference() {
        WeakReference weak = new WeakReference(new Car());
        assertNotNull(weak);

        System.gc();

//        assertNull(a);
        assertNull(weak.get());
    }

    class Car {
        private String name;

        public Car() {
            name = "car";
        }

        @Override
        public String toString() {
            return "Car{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
