package com.immoc.mall.service.impl;

import com.immoc.mall.dao.UserMapper;
import com.immoc.mall.enums.RoleEnum;
import com.immoc.mall.pojo.User;
import com.immoc.mall.service.IUserService;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.immoc.mall.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        // username不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(USERNAME_EXIST);
        }
        // email不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(EMAIL_EXIST);
        }

        // 给user默认加上一个角色
        user.setRole(RoleEnum.CUSTOMER.getCode());
        // 密码要用MD5摘要算法 Digest:摘要
        // Spring自带的MD5算法给密码加密
        user.setPassword(
                DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8))
        );
        // 将数据写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ResponseVo.error(ERROR);
        }
        // 注册成功
        return ResponseVo.sucess();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            // 用户不存在
            // 用户名或密码错误
            return ResponseVo.error(UERNAME_OR_PASSWORD_ERROR);
        }
        ;
        if (!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8))
        )) {
            // 密码错误
            return ResponseVo.error(UERNAME_OR_PASSWORD_ERROR);
        }
        // 不要返回密码
        user.setPassword(null);
        // 返回成功
        return ResponseVo.sucess(user);
    }

}
