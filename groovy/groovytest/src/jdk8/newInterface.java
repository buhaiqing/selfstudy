package jdk8;

import com.google.common.collect.testing.SampleElements;
import junit.framework.Test;

import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by buha on 12/8/2015.
 */
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

public class newInterface implements A, B {
    public static void main(String[] args) {
//        example1();
        Set<Integer> integers = new LinkedHashSet<>();
        integers.add(1);
        integers.add(3);
        integers.add(2);
        integers.forEach(x -> {
            System.out.println(x);
        });





//        Stream.iterate(0, n -> n + 1).limit(10).sequential().forEach(System.out::println);
//        long a =  1_000_000L;
//        System.out.println(a);
//        testSupplier(TestClass::new);

    }

    private static void example1() {
        List<Integer> integers = new ArrayList<>(Arrays.asList(2, 4, 3, 5, 6));

        int size = integers.size();

        List<Integer> o1 = IntStream.rangeClosed(1, integers.size()).boxed().collect(Collectors.toList());

        Collections.fill(o1, 1);
        o1.add(11);


        Collections.copy(o1, integers);
        System.out.println(o1.toString());
    }


    public static Predicate<TestClass> mytest() {
        return p -> p.getAge() > 20;
    }

    public void test() {

    }

    private static List<TestClass> callmytest(List<TestClass> list, Predicate<TestClass> o) {
//        return list.stream().filter(o).collect(toList());
        return list.stream().filter(TestClass::gg).collect(toList());
    }


    public static void testSupplier(Supplier<TestClass> o) {
        System.out.println("1");

        System.out.println("2");
        TestClass oo = o.get();
        System.out.println("3");
    }

}


class TestClass {
    public TestClass() {
        System.out.println("constructor in TestClass");
    }

    public int getAge() {
        return 20;
    }

    public static Predicate<TestClass> mytest() {
        return p -> p.getAge() > 20;
    }

    public static boolean gg(TestClass o) {
        return o.getAge() > 20;
    }

}

class AA extends TestClass {

    @Override
    public int getAge() {
        return 21;
    }
}

class BB extends TestClass {

}

interface A {
    default void test() {
        System.out.println(LocalDateTime.now());

    }
}

interface B {
    default void test1() {
        System.out.println("test1");
    }

//    default void test() {
//        System.out.println("another test");
//    }
}
