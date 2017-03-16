package com.mode.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

/**
 * Created by zhaoweiwei on 16/12/29.
 */
@Repository
public class TestRepo {

    private int c = 0;

    @Autowired
    public TestRepo(A a) {
        this.c = a.getA();
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public static void test(String... s) {
        for (String ss : s) {
            System.out.println(ss);
        }
    }

    public static void main(String[] args) {
        String[] list = new String[]{"a", "b"};

        SimpleBeanPropertyFilter.FilterExceptFilter filterExceptFilter =
                (SimpleBeanPropertyFilter.FilterExceptFilter)SimpleBeanPropertyFilter.filterOutAllExcept(list);
        System.out.println(filterExceptFilter.toString());
    }
}
