package com.mode.config;

/**
 * Created by zhaoweiwei on 16/10/13.
 */
public class Apple extends Fruit {

    @Override
    public void eat() {
        System.out.println("eat apple");
    }

    public static void eat(Fruit f) {
        System.out.println("eat fruit");
    }

    public static void eat(Apple f) {
        System.out.println("eat apple");
    }



    public static void main(String[] args) {
        Object o = new Apple();
        Class clazz = o.getClass();

        System.out.println(clazz);
    }
}
