package com.imooc.spring.aop.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Proxy1 implements UserService {
    UserService userService;

    public Proxy1(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser() {
        userService.createUser();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String now = sdf.format(new Date());
        System.out.println("---->" + now + userService.getClass().getName() + ".createUser");
    }
}
