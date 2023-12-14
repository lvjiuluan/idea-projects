package com.lagou.service.impl;

import com.lagou.dao.IUserDao;
import com.lagou.dao.impl.UserDaoImpl;
import com.lagou.service.IUserService;
import com.lagou.utils.BeanFactory;

public class UserServiceImpl implements IUserService {
    public void save() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        IUserDao userDao = new UserDaoImpl();
//        userDao.save();
        // 借助反射获取userDao实例
//        IUserDao userDao = (IUserDao) Class.forName("com.lagou.dao.impl.UserDaoImpl").newInstance();
//        userDao.save();
        // 借助工厂类
        IUserDao userDao = (IUserDao) BeanFactory.getBean("userDao");
        userDao.save();
    }
}
