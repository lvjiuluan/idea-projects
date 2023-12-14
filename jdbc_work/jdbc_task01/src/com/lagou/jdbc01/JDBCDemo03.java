package com.lagou.jdbc01;

import java.sql.*;

public class JDBCDemo03 {
    public static void main(String[] args){
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 1.注册驱动
//        Class.forName("com.mysql.jdbc.Driver");
            // 2.创建连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db4", "root", "147258");
            // 3.获取语句执行对象
            statement = con.createStatement();
            // 3.1 执行查询操作
            String sql = "select * from jdbc_user";
            resultSet = statement.executeQuery(sql);
            // 3.2 处理结果集对象

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //这个代码块的代码始终会执行
            // 4.关闭流
            try {
                resultSet.close();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }
}
