package com.lagou.xml05;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testJDBC {
    public static void main(String[] args) throws SQLException {
        // 查询所有的员工信息
        Connection con = JDBCUtils.getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from employee");
        System.out.println(resultSet);
        statement.close();
        con.close();
    }
}
