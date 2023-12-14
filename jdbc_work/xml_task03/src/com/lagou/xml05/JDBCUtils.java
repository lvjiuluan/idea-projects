package com.lagou.xml05;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    // 保存连接信息
    public static String DRIVERNAME;
    public static String URL;
    public static String USERNAME;
    public static String PASSWORD;
    // 在静态代码块中读取XML信息
    static {
        try {
            // 使用XPATH语法
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read("D:\\IDEA Projects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml05\\jdbc-config.xml");
            DRIVERNAME = document.selectSingleNode("/jdbc/property[@name='driverClass']").getText();
            URL = document.selectSingleNode("/jdbc/property[@name='jdbcUrl']").getText();
            USERNAME = document.selectSingleNode("/jdbc/property[@name='user']").getText();
            PASSWORD = document.selectSingleNode("/jdbc/property[@name='password']").getText();
            // 注册驱动
            Class.forName(DRIVERNAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }
}
