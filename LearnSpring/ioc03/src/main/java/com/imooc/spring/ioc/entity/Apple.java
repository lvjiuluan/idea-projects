package com.imooc.spring.ioc.entity;

import lombok.Data;

@Data
public class Apple {
    private String tittle;
    private String color;
    private String original;

    public Apple() {
        System.out.println("默认构造方法创建对象");
    }

    public Apple(String tittle, String color, String original) {
        System.out.println("调用有参构造 3个参数");
        this.tittle = tittle;
        this.color = color;
        this.original = original;
    }

    public Apple(String tittle, String color) {
        System.out.println("2个参数的构造方法");
        this.tittle = tittle;
        this.color = color;
    }

    public Apple(String tittle, Integer i) {
        System.out.println("2个参数的构造方法，有int");
        this.tittle = tittle;
    }
}
