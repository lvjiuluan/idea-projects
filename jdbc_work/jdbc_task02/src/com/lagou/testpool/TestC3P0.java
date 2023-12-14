package com.lagou.testpool;

import com.lagou.utils.C3P0Utils;

import java.sql.*;

public class TestC3P0 {
    /*
    * 查询姓名为李白的记录
    * */
    public static void main(String[] args) throws SQLException {
        // 1.获取连接
        Connection con = C3P0Utils.getConnection();
        // 2.获取语句执行平台
        String sql = "select * from employee where ename = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        // 2.1 设置占位符的值
        ps.setString(1,"李白");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            int eid = resultSet.getInt("eid");
            String ename = resultSet.getString("ename");
            int age = resultSet.getInt("age");
            String sex = resultSet.getString("sex");
            double salary = resultSet.getDouble("salary");
            Date empdate = resultSet.getDate("empdate");

            System.out.println("eid: "+eid+" ename: "+ename+" age: "+age+" sex: "+sex+" salary: "+salary+" empdate: "+empdate);
        }
        // 关闭连接
        C3P0Utils.close(con,ps,resultSet);
    }
}
