package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;

import java.util.Map;

public interface IUserService {
    // 根据用户id查询用户
    // 尽量保持一致，学习别人怎么命名
    User findUserById(Integer id);

    // 注册的业务
    Map<String, Object> register(User user);

    // 激活新注册的账号
    Integer activation(Integer id, String activationCode);

    // 登录的业务
    Map<String, Object> login(String username, String password, Long expiredSession);
}
