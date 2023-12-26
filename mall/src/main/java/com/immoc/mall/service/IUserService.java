package com.immoc.mall.service;

import com.immoc.mall.pojo.User;
import com.immoc.mall.vo.ResponseVo;

public interface IUserService {
    // 注册
    ResponseVo register(User user);
    // 登录

}
