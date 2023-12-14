package com.lagou.jdbc01;

import java.sql.*;

public class JDBCDemo02 {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2.创建连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db4", "root", "147258");
        // 3.生成语句执行平台
        Statement statement = con.createStatement();
        // 3.1 执行查询操作
        String sql = "select * from jdbc_user;";
        ResultSet resultSet = statement.executeQuery(sql);
//        boolean next = resultSet.next(); // 判断是否有下一条数据
//        System.out.println(next);
        // 获取id
//        int id = resultSet.getInt("id");
//        System.out.println("通过列名的方式获取 " + id);
//        int id = resultSet.getInt(1);
//        System.out.println("通过列号获取id "+ id);
        // 通过while循环获取数据
        while (resultSet.next()){
            // 获取id
            int id = resultSet.getInt("id");
            // 获取用户名
            String username = resultSet.getString("username");
            // 获取密码
            String password = resultSet.getString("password");
            // 获取生日
            Date birthday = resultSet.getDate("birthday");

            System.out.println(id+": "+username+": "+password+": "+birthday);
        }

        // 4.关闭流
        resultSet.close();
        statement.close();
        con.close();
    }
}
