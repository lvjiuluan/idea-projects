package com.imooc.spring.ioc.controller;

import com.imooc.spring.ioc.service.UserService;

public class BookController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }

    public BookController() {
        System.out.println("BookController对象已创建" + this);
    }

    public void addBook() {
        System.out.println(this + "调用了addBook方法");
    }
}
