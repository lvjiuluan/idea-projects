package com.lagou.task02_demo1.dao;

import com.lagou.task02_demo1.model.User;
import com.lagou.task02_demo1.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public int createUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 获取连接
            connection = DBUtil.getConnection();
            // 准备SQL语句
            String sql = "insert into t_user values(null,?,?)";
            // 获取语句执行平台
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            int row = preparedStatement.executeUpdate();
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            try {
                DBUtil.closeConnection(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0; //执行失败
    }
}
