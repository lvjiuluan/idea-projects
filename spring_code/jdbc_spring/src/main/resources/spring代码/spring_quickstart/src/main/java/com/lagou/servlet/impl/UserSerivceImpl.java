package com.lagou.servlet.impl;

import com.lagou.dao.IUserDao;
import com.lagou.servlet.IUserService;

public class UserSerivceImpl implements IUserService {

    private IUserDao userDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        // 调用dao层的save方法
        userDao.save();


    }
}
