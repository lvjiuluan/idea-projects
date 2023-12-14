package com.lagou.testpool;

import com.lagou.utils.DBCPUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBCP {
    /*
    * 测试DBCP的连接方法
    * */
    public static void main(String[] args) throws SQLException {
        // 1.从DBCP连接池中拿到连接
        Connection con = DBCPUtils.getConnection();
        // 2.语句执行对象
        Statement statement = con.createStatement();
        // 3.对数据库进行查询操作, 查询所有员工的姓名
        String sql = "select ename from employee";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            String ename = resultSet.getString("ename");
            System.out.println("姓名: " + ename);
            /*for (int i = 0; i < ename.length(); i++) {
                char ch = ename.charAt(i);
                System.out.println(ch+","+Character.getNumericValue(ch));
            }*/
        }
        // 4.归还连接
        DBCPUtils.close(con,statement,resultSet);
    }
}
