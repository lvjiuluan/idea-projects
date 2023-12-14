package com.lagou.jdbc03;

import com.lagou.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestLogin01 {
    /*
    * 用户登录案例
    * */
    public static void main(String[] args) throws SQLException {
        // 1.获取连接
        Connection con = JDBCUtils.getConnection();
        // 2.获取Statement对象
        Statement statement = con.createStatement();
        // 3.获取用户输入的用户名和密码
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        System.out.println("你好：'"+ username +"'");
        // 拼接SQL语句
        String sql = "select * from jdbc_user where username='"+username+"' and password = '"+ password +"'";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        // 处理结果集
//        System.out.println(resultSet.next());
        if (resultSet.next()){
            System.out.println("登录成功！ 欢迎您："+ username);
        }else {
            System.out.println("登录失败！");
        }
        JDBCUtils.close(con,statement,resultSet);
    }
}
