package com.imooc.spring.ioc.service;


import com.imooc.spring.ioc.SpringApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends SpringApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void register(){
        userService.register();
    }
}