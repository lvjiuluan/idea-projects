package com.imooc.spring.ioc.entity;

public class Apple {
    private String tittle;
    private String color;
    private String original;

    public Apple() {
        System.out.println("Apple默认构造函数被调用: " + this);
    }

    public Apple(String tittle, String color, String original) {
        this.tittle = tittle;
        this.color = color;
        this.original = original;
    }

    public String getTittle() {
        return tittle;
    }

    public String getColor() {
        return color;
    }

    public String getOriginal() {
        return original;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
