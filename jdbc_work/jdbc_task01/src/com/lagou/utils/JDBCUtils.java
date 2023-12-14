package com.lagou.utils;

import java.sql.*;

public class JDBCUtils {
    // 1.将连接信息定义为: 字符串常量
    public static final String DRIVERNAME = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/db4?characterEncoding=UTF-8";
    public static final String USER = "root";
    public static final String PASSWORD = "147258";
    // 2.编写静态代码块，执行注册驱动
    static {

        try {
            Class.forName(DRIVERNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // 3. 获取连接的静态方法
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 4.关闭流的方法
    public static void close(Connection con, Statement statement){
        if (con != null && statement != null){
            try {
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // 4.关闭流的方法 重载
    public static void close(Connection con, Statement statement, ResultSet resultSet){
        if (con != null && statement != null && resultSet != null){
            try {
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
