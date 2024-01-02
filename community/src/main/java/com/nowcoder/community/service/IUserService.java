package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;

public interface IUserService {
    // 根据用户id查询用户
    // 尽量保持一致，学习别人怎么命名
    User findUserById(Integer id);
}
