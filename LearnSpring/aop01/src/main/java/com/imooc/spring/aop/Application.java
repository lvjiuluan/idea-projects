package com.imooc.spring.aop;

import com.imooc.spring.aop.service.Proxy1;
import com.imooc.spring.aop.service.UserServiceImpl;

public class Application {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        Proxy proxy = new Proxy(userService);
        Proxy1 proxy1 = new Proxy1(proxy);
        proxy1.createUser();
    }
}
