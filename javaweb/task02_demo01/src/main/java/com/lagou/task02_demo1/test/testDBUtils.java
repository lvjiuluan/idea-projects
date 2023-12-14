package com.lagou.task02_demo1.test;

import com.lagou.task02_demo1.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class testDBUtils {
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            System.out.println("连接数据库成功！");
        } catch (SQLException e) {
            System.out.println("111");
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
