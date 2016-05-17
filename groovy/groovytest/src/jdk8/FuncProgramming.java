package jdk8;

import com.sun.org.apache.xpath.internal.functions.FuncId;
import rx.functions.Func1;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by buha on 5/14/2016.
 */
public class FuncProgramming {
    public static void main(String[] args) {
        List<Integer> myints = Arrays.asList(1, 2, 3, 1);
        myints.stream().distinct().forEach(o -> System.out.println(o));

        myints.stream().findFirst();
        Predicate<String> a = (String s) -> true;

        Func1<String,String>

    }
}
