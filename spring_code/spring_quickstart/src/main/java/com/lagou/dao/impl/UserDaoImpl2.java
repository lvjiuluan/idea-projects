package com.lagou.dao.impl;

import com.lagou.dao.IUserDao;

public class UserDaoImpl2 implements IUserDao {
    @Override
    public void save() {
        System.out.println("UserDaoImpl2被调用了");
    }
}
