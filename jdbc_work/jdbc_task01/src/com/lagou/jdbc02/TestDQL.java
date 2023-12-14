package com.lagou.jdbc02;

import com.lagou.utils.JDBCUtils;

import java.sql.*;

public class TestDQL {
    public static void main(String[] args) throws SQLException {
        Connection con = JDBCUtils.getConnection();
        Statement statement = con.createStatement();
        String sql = "select * from jdbc_user where username='zhangbaiwan'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Date birthday = resultSet.getDate("birthday");
            System.out.println(id + ": " + username + ": " + password + ": " + birthday);
        }
        JDBCUtils.close(con,statement,resultSet);
    }
}
