package com.lagou.jdbc03;

import com.lagou.utils.JDBCUtils;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestPS {
    public static void main(String[] args) throws SQLException, ParseException {
        Connection con = JDBCUtils.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate("insert into jdbc_user values (null ,'zhansan','123','2020/6/18')");
        statement.executeUpdate("insert into jdbc_user values (null ,'lisi','123','2020/6/18')");

        PreparedStatement ps = con.prepareStatement("insert into jdbc_user values (?,?,?,?)");
        ps.setObject(1,null);
        ps.setString(2,"wangwu");
        ps.setString(3,"123");
        Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2020/12/4");
        ps.setDate(4, new java.sql.Date(date.getTime()));
        ps.executeUpdate();
        ps.close();
        JDBCUtils.close(con,statement);

    }
}
