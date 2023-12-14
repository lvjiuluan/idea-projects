package com.lagou.app;

import com.lagou.dao.UserDao;
import com.lagou.entity.User;
import com.lagou.utils.DateUtils;
import com.lagou.utils.UUIDUtils;
import org.junit.Test;

import java.sql.SQLException;

/**
 * 测试注册用户
 */
public class TestUserDao {
    UserDao userDao = new UserDao();
    // 注册用户
    @Test
    public void testRegister() throws SQLException {
        User user = new User(
                UUIDUtils.getUUID(),
                "武松",
                "123456",
                "10086",
                DateUtils.getDateFormart(),
                "男"
        );
        System.out.println(user);
        // 执行注册
        int i = userDao.register(user);
        if (i > 0){
            System.out.println("注册成功， 欢迎您"+user.getUsername());
        }else {
            System.out.println("注册失败");
        }
    }
    // 测试用户登录
    @Test
    public void testLogin() throws SQLException {
        User user = userDao.login("武松", "123456");
        if (user != null){
            System.out.println("登录成功，欢迎您 "+user.getUsername());
        }else {
            System.out.println("登录失败，用户名或者密码错误");
        }
    }
}
