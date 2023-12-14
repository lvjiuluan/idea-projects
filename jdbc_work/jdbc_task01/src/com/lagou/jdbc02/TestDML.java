package com.lagou.jdbc02;

import com.lagou.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDML {
    /*
    * 插入数据
    * */
    @Test
    public void testInsert(){
        // 通过jdbcUtils工具来获取连接
        Connection connection = JDBCUtils.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "insert into jdbc_user values(null,'zhangbaiwan','123','2020/6/18');";
            int i = statement.executeUpdate(sql); //表示受影响的行数
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 3. 关闭流
        JDBCUtils.close(connection,statement);
    }
    /*
    * 测试更新操作
    * */
    @Test
    public void testUpdate() throws SQLException {
        // 根据id修改用户名
        Connection con = JDBCUtils.getConnection();
        Statement statement = con.createStatement();
        String sql = "update jdbc_user set username='liuneng' where id=1";
        int i = statement.executeUpdate(sql);
        System.out.println(i);
        JDBCUtils.close(con,statement);
    }
    /*
    * 测试删除操作
    * */
    @Test
    public void testDelete() throws SQLException {
        Connection con = JDBCUtils.getConnection();
        Statement statement = con.createStatement();
        String sql = "delete from jdbc_user where id in (1,2);";
        int i = statement.executeUpdate(sql);
        System.out.println(i);
        JDBCUtils.close(con,statement);
    }
}
