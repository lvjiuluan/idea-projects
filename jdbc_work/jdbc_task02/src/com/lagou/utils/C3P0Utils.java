package com.lagou.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class C3P0Utils {
    // 1.创建连接池对象，C3P0对DataSource的实现类
    // 这种方式使用的是xml中的默认配置
//    public static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    // 使用指定的配置
    public static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");
    // 2.获取连接的方法
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    // 3.释放资源方法
    public static void close(Connection con, Statement statement){
        if(con != null && statement!= null){
            try {
                // 归还连接
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection con, Statement statement, ResultSet resultSet){
        if(con != null && statement != null && resultSet != null){
            try {
                // 归还连接
                resultSet.close();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
