/*
package com.lagou.testpool;

import com.lagou.utils.DruidUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDruid {
    // 需求 查询工资 3000到5000 之间员工的姓名
    public static void main(String[] args) throws SQLException {
        // 1 获取连接对象
        Connection con = DruidUtils.getConnection();
        // 2 获取语句执行平台
        Statement statement = con.createStatement();
        // 2.1 执行SQL语句
        ResultSet resultSet = statement.executeQuery("select ename from employee where salary between 3000 and 5000");
        // 2.2 处理结果集对象
        while (resultSet.next()){
            System.out.println(resultSet.getString("ename"));
        }
        // 3 关闭连接
        DruidUtils.close(con,statement,resultSet);
    }
}
*/
