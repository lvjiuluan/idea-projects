package com.exmaple.spring.service;

import com.exmaple.spring.dao.UserDao;

public class UserService {

    private UserDao userDao;

    public void add() {
        userDao.insert();
    }

    public void remove() {
        userDao.delete();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


}
