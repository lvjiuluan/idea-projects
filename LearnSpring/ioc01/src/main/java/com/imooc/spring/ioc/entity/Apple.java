package com.imooc.spring.ioc.entity;

import lombok.Data;

@Data
public class Apple {
    private String tittle;
    private String color;
    private String original;

    public Apple() {
    }

    public Apple(String tittle, String color, String original) {
        this.tittle = tittle;
        this.color = color;
        this.original = original;
    }
}
