package com.lagou.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtils {
    // 1.定义成员变量
    public static DataSource dataSource;
    // 2.静态代码块
    static {
        try {
            // 创建属性集对象
            Properties p = new Properties();
            // Druid不能够主动加载配置文件，需要手动指定
            InputStream resourceAsStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            // Properties 的load方法，从字节流读取配置信息
            p.load(resourceAsStream);
            // 多态，父类指向子类对象
            dataSource = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 获取Druid连接池对象的方法
    public static DataSource getDataSource(){
        return dataSource;
    }

    // 获取连接的方法
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    // 关闭连接方法
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
