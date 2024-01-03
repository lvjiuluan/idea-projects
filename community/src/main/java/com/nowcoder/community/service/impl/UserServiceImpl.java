package com.nowcoder.community.service.impl;

import com.nowcoder.community.config.MailConfig;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public User findUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        // 1 对传进来的值进行空判断
        // 1.1 空处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        // 1.2 判断username是不是空串 null "" " "
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        // 1.3 判断password是不是空串
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("usernameMsg", "密码不能为空");
            return map;
        }
        // 1.4 判断邮箱是不是空串
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        // 2 判断用户名、邮箱是否重复
        User tempUser = userMapper.selectByUsername(user.getUsername());
        if (tempUser != null) {
            map.put("usernameMsg", "该账号已存在！");
            return map;
        }
        tempUser = userMapper.selectByEmail(user.getEmail());
        if (tempUser != null) {
            map.put("emailMsg", "该邮箱已被注册！");
            return map;
        }
        // 3 注册用户，将用户的信息保存到数据库
        // 3.1 要将用户的密码加密，加密需要加盐处理
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0); // 默认普通用户
        user.setStatus(0); // 默认未激活
        user.setActivationCode(CommunityUtil.generateUUID()); // 激活码，随机字符串
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        userMapper.insertSelective(user);
        // 4 给用户发送激活邮件
        // 4.1 需要从thymeleaf模板引擎中获取html
        // 获取thymeleaf的Context
        Context context = new Context();
        return map;
    }


}
