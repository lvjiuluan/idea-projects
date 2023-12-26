package com.immoc.mall.service.impl;

import com.immoc.mall.dao.UserMapper;
import com.immoc.mall.pojo.User;
import com.immoc.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        // username不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            throw new RuntimeException("该username已注册");
        }
        // email不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            throw new RuntimeException("该username已注册");
        }
        // 密码要用MD5摘要算法 Digest:摘要
        // Spring自带的MD5算法给密码加密
        user.setPassword(
                DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8))
        );
        // 将数据写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            throw new RuntimeException("注册失败");
        }
        // 注册成功
    }
}
