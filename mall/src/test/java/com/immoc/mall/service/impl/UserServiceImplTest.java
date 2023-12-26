package com.immoc.mall.service.impl;

import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.enums.RoleEnum;
import com.immoc.mall.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class UserServiceImplTest extends MallApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void register() {
        User user = new User("jack", "123456", "jack@qq.com", RoleEnum.ADMIN.getCode());

        userService.register(user);
    }
}