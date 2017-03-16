package com.mode.test;

import org.springframework.stereotype.Component;

/**
 * Created by zhaoweiwei on 16/12/29.
 */
public class A {

    private int a = 0;

    public A(int a) {
        this.a = a;
    }


    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
