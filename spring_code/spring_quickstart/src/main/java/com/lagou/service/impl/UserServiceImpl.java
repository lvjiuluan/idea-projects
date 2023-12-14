package com.lagou.service.impl;

import com.lagou.dao.IUserDao;
import com.lagou.service.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImpl implements IUserService {

    private IUserDao userDao; // 用于接收注入进来的userDao实例

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }


//    public UserServiceImpl(IUserDao userDao) {
//        this.userDao = userDao;
//    }

    @Override
    public void save() {
        this.userDao.save();
    }
}
