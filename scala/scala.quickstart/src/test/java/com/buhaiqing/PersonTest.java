package com.buhaiqing;

import org.junit.Test;

import static org.junit.Assert.*;

import scala.com.buhaiqing.chapter1.Person;

/**
 * Created by buha on 11/25/2015.
 */
public class PersonTest {
    @Test
    public void test1() {
        Person p = new Person("andy", 36);
        assertEquals(p.getName(), "andy");
        p.setAge(37);
        assertEquals(p.getAge(), 37);
        p.run();
    }
}
