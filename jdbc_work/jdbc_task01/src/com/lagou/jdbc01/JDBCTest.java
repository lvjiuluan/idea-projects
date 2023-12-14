package com.lagou.jdbc01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCTest {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动 （这一步不是必须的）
        Class.forName("com.mysql.jdbc.Driver");
        // 2.创建连接
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db4", "root", "147258");
        // 3.执行SQL，在语句平台上执行
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from jdbc_user;");
        System.out.println(resultSet);
        // 4.关闭连接，先开后闭
        resultSet.close();
        statement.close();
        con.close();
    }
}
