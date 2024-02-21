package com.imooc.spring.aop;

import com.imooc.spring.aop.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Proxy implements UserService {

    UserService userService;

    public Proxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String now = sdf.format(new Date());
        System.out.println("---->" + now + userService.getClass().getName() + ".createUser");
        userService.createUser();
    }
}
