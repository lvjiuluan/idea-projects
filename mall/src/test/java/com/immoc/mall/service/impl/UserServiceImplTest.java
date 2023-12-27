package com.immoc.mall.service.impl;

import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.enums.RoleEnum;
import com.immoc.mall.pojo.User;
import com.immoc.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class UserServiceImplTest extends MallApplicationTests {
    public static final String USERNAME = "jack";
    public static final String PASSWORD = "14725";

    @Autowired
    private UserServiceImpl userService;

    @Test
    void register() {
        User user = new User(USERNAME, PASSWORD, "jack@qq.com", RoleEnum.ADMIN.getCode());
        userService.register(user);
    }

    @Test
    void login() {
        register();
        ResponseVo<User> userResponseVo = userService.login(USERNAME, PASSWORD);
        Integer status = userResponseVo.getStatus();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), status);
    }
}