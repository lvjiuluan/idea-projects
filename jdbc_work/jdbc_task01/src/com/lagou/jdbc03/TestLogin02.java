package com.lagou.jdbc03;

import com.lagou.utils.JDBCUtils;

import java.sql.*;
import java.util.Scanner;

public class TestLogin02 {
    /*
    * SQL注入：
    * 用户输入的用户名和密码与我们编写的sql进行了拼接
    * 用户输入人的内容成为了sql语法的一部分，用户会利用
    * 这个漏洞，输入一些其它的字符串，改变sql原有的含义
    * 如何解决：不能让用户输入的数据和sql直接拼接
    * 预处理对象,preparedStatement，Statement接口的子接口
    *  a. 有一个预编译的功能，提高SQL执行的效率
    *  b. 通过占位符的方式 设置参数 防止SQL注入
    * */
    public static void main(String[] args) throws SQLException {
        // 1.获取连接
        Connection con = JDBCUtils.getConnection();
        // 2.获取PreparedStatement对象，传入sql，预编译
        String sql = "select * from jdbc_user where username = ? and password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        // 3.获取用户输入的用户名和密码
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        System.out.println("你好：'"+ username +"'");
        // 4.设置参数,ps.setXXX(占位符的位置，要设置的值)
        ps.setString(1,username);
        ps.setString(2,password);
        // 5.执行查询
        ResultSet resultSet = ps.executeQuery();
        // 6.处理结果
        if (resultSet.next()){
            System.out.println("登录成功！ 欢迎您："+ username);
        }else {
            System.out.println("登录失败！");
        }
        // 7.关闭流
        JDBCUtils.close(con,ps,resultSet); // 这里用到了多态
    }
}
