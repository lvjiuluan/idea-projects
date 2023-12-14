package com.lagou.testBatch;

import com.lagou.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/***
 * 使用批处理，向表中添加一万调数据
 */
public class BatchInsert {
    public static void main(String[] args) throws SQLException {
        // 1 获取连接
        Connection con = DruidUtils.getConnection();
        // 2 获取语句执行对象
        PreparedStatement ps = con.prepareStatement("insert into testBatch(uname) values(?)");
        // 2.1 执行批量插入操作
        for (int i = 0; i < 10000; i++) {
            // 设置占位符的值
            ps.setString(1,"小强"+i);
            // 将SQL添加到批处理列表
            ps.addBatch();
        }
        // 添加时间戳，测试sql执行效率
        long start = System.currentTimeMillis();
        ps.executeBatch();
        long end = System.currentTimeMillis();
        System.out.println("插入一万条数据需要执行："+(end-start)+"毫秒");
        // 关闭流对象
        DruidUtils.close(con,ps);
    }
}
