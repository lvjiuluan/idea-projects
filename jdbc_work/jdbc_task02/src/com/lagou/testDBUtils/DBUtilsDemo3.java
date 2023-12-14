package com.lagou.testDBUtils;

import com.lagou.entity.Employee;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBUtilsDemo3 {
    /***
     * 查询eid为5的数据，封装到数组当中
     * ArrayHandler，将结果集中的第一条数据，封装到数组当中
     */
    @Test
    public void testFindByID() throws SQLException {
        // 1 创建QueryRunner对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2 编写sql语句
        String sql = "select * from employee where eid=?";
        // 3 设置占位符的参数
        Object param = 5;
        Object[] query = qr.query(sql, new ArrayHandler(), param);//中间传入的是对象，利用多态
        // 4 获取数据
        System.out.println(Arrays.toString(query));
    }
    /***
     * 查询eid为5的数据
     * ArrayListHandler，先将每一条记录封装到数组中，再将数组封装到集合中
     */
    @Test
    public void testFindAll() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee";
        List<Object[]> query = qr.query(sql, new ArrayListHandler());
        for (Object[] objects : query) {
            System.out.println(Arrays.toString(objects));
        }
    }
    /***
     * 查询所有数据，封装到list集合当中
     * 将查询到的数据封装到Javabean当中，JavaBean是指定的
     * BeanHander实现类：将结果集的第一条数据封装到JavaBean中
     * Employee
     */
    @Test
    public void testFindByIDJavaBean() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where eid=?";
        // 3 设置占位符的参数
        Object param = 3;
        Employee query = qr.query(sql, new BeanHandler<Employee>(Employee.class), param);
        System.out.println(query);
    }
    /***
     * 查询薪资大于3000的员工，封装到javabean，再封装到集合
     * BeanListHandler
     */
    @Test
    public void testFindBySalaryJavaBean() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where salary>=?";
        // 3 设置占位符的参数
        Object param = 3000;
        List<Employee> query = qr.query(sql, new BeanListHandler<Employee>(Employee.class), param);
        for (Employee employee : query) {
            System.out.println(employee);
        }
    }
    /***
     * 查询姓名为张百万的员工信息，结果封装到map集合中
     * MapHandler：将结果集的第一条记录封装到Map<String,Object> key-value
     * key:列名，value：值
     */
    @Test
    public void testFindByName() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where ename=?";
        Object param = "张百万";
        Map<String, Object> query = qr.query(sql, new MapHandler(), param);
        System.out.println(query.keySet());
        System.out.println(query);
        Set<Map.Entry<String, Object>> entries = query.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            System.out.println(entry.getKey() + " = "+entry.getValue());
        }

    }
}
