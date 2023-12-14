package com.lagou.testDBUtils;

import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/***
 * 使用QueryRunner类的对象完成增删改的操作
 */
public class DBUtilsDemo2 {
    /***
     * 插入操作
     * @throws SQLException
     */
   /* @Test
    public void testInsert() throws SQLException {
        // 创建QueryRunner对象
        QueryRunner qr = new QueryRunner();
        // 插入一条数据
        // 编写sql，用占位符方式
        String sql = "insert employee values(?,?,?,?,?,?)";
        // 设置占位符的参数
        Object[]  param = {null,"张百万",20,"女",10000,"1999-9-9"};
        Connection con = DruidUtils.getConnection();
        int i = qr.update(con, sql, param);
        // 释放资源
        con.close();
    }*/
    // 修改操作，修改员工姓名为张百万的薪资为15000
    @Test
    public void testUpdate() throws SQLException {
        DataSource dataSource = DruidUtils.getDataSource();
        QueryRunner qr = new QueryRunner(dataSource);
        qr.update("update employee set salary = salary+1000 where ename='张百万'");
    }
    // 删除操作，eid为1的数据删除
    @Test
    public void testDelete() throws SQLException {
        DataSource dataSource = DruidUtils.getDataSource();
        QueryRunner qr = new QueryRunner(dataSource);
        qr.update("delete from employee where eid=1");
    }

}
