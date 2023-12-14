package com.lagou.utils;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCPUtils {
    // 1.定义常量，保存数据库连接的相关信息
    public static final String DRIVERNAME = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/db5?characterEncoding=UTF-8";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "147258";
    // 2.创建连接池对象，由DBCP提供的实现类
    public static BasicDataSource dataSource = new BasicDataSource();
    // 3.使用静态代码块进行配置
    static {
        dataSource.setDriverClassName(DRIVERNAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaxActive(20); //设置最大连接数量
    }
    // 4.编写获取连接的方法
    public static Connection getConnection() throws SQLException {
        // 从连接池中获取连接
        return dataSource.getConnection();
    }
    // 5.释放资源方法
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
