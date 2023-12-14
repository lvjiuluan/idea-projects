package com.lagou.task02_demo1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static String jdbcName; // 描述驱动信息
    private static String dbURL; // 描述URL信息
    private static String dbUsername; // 描述用户名信息
    private static String dbPassword; // 用于描述密码信息

    static {
        jdbcName = "com.mysql.jdbc.Driver";
        dbURL = "jdbc:mysql://localhost:3306/db_web?characterEncoding=UTF-8";
        dbUsername = "root";
        dbPassword = "147258";
        try {
            // 注册驱动
            Class.forName(jdbcName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*
    * 获取连接
    * */
    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        return con;
    }

    /***
     * 关闭连接
     * @param con
     * @throws SQLException
     */
    public static void closeConnection(Connection con) throws SQLException {
        if(null != con){
            con.close();
        }
    }
    public static void closeConnection(Connection con, Statement statement) throws SQLException {
        if(null != con && null != statement){
            statement.close();
            con.close();
        }
    }

}
