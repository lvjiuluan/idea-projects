package com.imooc.spring.ioc.entity;

public class Apple {
    private String title;
    private String color;
    private String original;

    public Apple() {
    }

    public Apple(String title, String color, String original) {
        this.title = title;
        this.color = color;
        this.original = original;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public String getOriginal() {
        return original;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
