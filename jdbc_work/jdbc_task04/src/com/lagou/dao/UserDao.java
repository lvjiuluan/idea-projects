package com.lagou.dao;

import com.lagou.entity.User;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/***
 * 对数据库中User表的相关操作
 */
public class UserDao {
    // 1 向Uer表中插入一条数据(一个User 实例)
    public int register(User user) throws SQLException {
        // 1.1 获取QueryRunner对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 1.2 编写SQL
        String sql = "insert into user values (?,?,?,?,?,?)";
        Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),
        user.getTelepthon(),user.getBirthday(),user.getSex()};
        int i = qr.update(sql, params);
        return i;
    }
    // 2 登录方法
    public User login(String username, String password) throws SQLException {
        // 2.1 创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2.2 编写SQL
        String sql = "select * from user where username=? and password=?";
        User user = qr.query(sql, new BeanHandler<User>(User.class), username, password);
        return user;
    }
}
