package com.imooc.spring.ioc.learn;

import java.util.HashSet;
import java.util.Set;

public class learn01 {
    public static void main(String[] args) {
        A a1 = new A(1);
        A a2 = new A(1);
        Set<A> set = new HashSet<>();
        set.add(a1);
        set.add(a2);
        set.add(a1);
        Integer i = 0;
        System.out.println("set = " + set.toString().replace("[","{").replace("]","}"));
    }
}

class A{
    int i;

    public A(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return String.valueOf(i);
    }
}
