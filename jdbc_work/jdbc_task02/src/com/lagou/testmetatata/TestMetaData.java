package com.lagou.testmetatata;

import com.lagou.utils.DruidUtils;

import java.sql.*;

public class TestMetaData {
    public static void main(String[] args) throws SQLException {
        Connection con = DruidUtils.getConnection();
        // 数据库元数据信息
        DatabaseMetaData metaData = con.getMetaData();
        System.out.println(metaData.getURL());
        System.out.println(metaData.getUserName());
        System.out.println(metaData.getDatabaseProductName());
        System.out.println(metaData.getDatabaseProductVersion());
        System.out.println(metaData.getDriverName());
        System.out.println(metaData.isReadOnly());
        // 关闭连接
        System.out.println("*************************************");
        /***
         * 获取结果集
         */
        PreparedStatement ps = con.prepareStatement("use db4;");
//        ResultSet resultSet = ps.executeQuery();
        /*ResultSetMetaData metaData1 = ps.getMetaData();
        System.out.println(metaData1.getColumnCount());
        System.out.println(metaData1.getColumnName(1));
        System.out.println(metaData1.getColumnTypeName(1));
        for (int i = 0; i < metaData1.getColumnCount(); i++) {
            System.out.println(metaData1.getColumnName(i+1)+","+metaData1.getColumnTypeName(i+1));
        }*/
        DruidUtils.close(con,ps);
//        DruidUtils.close(con,ps,resultSet);
    }
}
