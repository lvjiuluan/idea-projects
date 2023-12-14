package com.lagou.jdbc04;

import com.lagou.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJDBCTransaction {
    // 使用JDBC操作事务
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            // 1.获取连接
            con = JDBCUtils.getConnection();
            // 2.开启事务
            con.setAutoCommit(false); //手动提交事务
            // 3.执行SQL (执行两次修改)
            // 3.1 tom账户减500块钱
            ps = con.prepareStatement("update account set money = money-? where name = ?");
            ps.setDouble(1,500.0);
            ps.setString(2,"Tom");
            ps.executeUpdate();

            // 模拟tom转账之后出现异常
            System.out.println(1 / 0);

            // 3.1 jack账户加500块钱
            ps = con.prepareStatement("update account set money = money+? where name = ?");
            ps.setDouble(1,500.0);
            ps.setString(2,"jack");
            ps.executeUpdate();

            // 4.提交事务
            con.commit();
            System.out.println("转账成功");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("运行到这里");
            // 5.出现异常就回滚事务
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            // 6.释放资源
            JDBCUtils.close(con,ps);
        }



    }
}
