package com.imooc.spring.ioc.entity;

import lombok.Data;

@Data
public class Child {
    private String name;
    private Apple apple;

    public void eat() {
        System.out.println(name + "吃到了" + apple.getOriginal() +
                "种植的" + apple.getTittle());
    }

    public Child(String name, Apple apple) {

        this.name = name;
        this.apple = apple;
    }

    public Child() {
        System.out.println("正在创建child对象...");
    }
}
