package com.imooc.spring.ioc.entity;

public class Child {
    private String name;
    private Apple apple;

    public void eat() {
        System.out.println(name + "吃到了" + apple.getOriginal() +
                "种植的" + apple.getTittle());
    }

    public String getName() {
        return name;
    }

    public Apple getApple() {
        return apple;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public Child(String name, Apple apple) {
        this.name = name;
        this.apple = apple;
    }

    public Child() {
    }
}
