package com.lagou.mapper;

import com.lagou.domain.Orders;
import com.lagou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {

    /**
     * 一对一关联查询， 查询所有订单，于此同时还要查询每个订单所属用户
     */
    @Test
    public void test1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        // 下面写业务
        List<Orders> orders = mapper.findAllWithUser();
        for (Orders order : orders) {
            System.out.println(order);
        }
        sqlSession.close();
    }

    /**
     * 一对多查询，查询每个用户，并将每个用户关联的订单信息打印
     */
    @Test
    public void test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 下面写业务
        List<User> users = mapper.findAllWithOrder();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 多对多关联查询，查询所有用户以及对应的角色信息
     */
    @Test
    public void test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 下面写业务
        for (User user : mapper.findAllWithRole()) {
            System.out.println(user);
        }


    }

    /**
     * 一对一 嵌套查询， 查询所有订单，于此同时还要查询每个订单所属用户
     */
    @Test
    public void test4() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        // 下面写业务
        List<Orders> orders = mapper.findAllWithUser2();
        for (Orders order : orders) {
            System.out.println(order);
        }
        sqlSession.close();
    }

    /**
     * 一对多嵌套查询，查询每个用户，并将每个用户关联的订单信息打印
     */
    @Test
    public void test5() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 下面写业务
        List<User> users = mapper.findAllWithOrder2();
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getOrdersList());
            System.out.println("*******************************************");
        }
    }

    /**
     * 多对多嵌套查询，查询所有用户以及对应的角色信息
     */
    @Test
    public void test6() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 下面写业务
        for (User user : mapper.findAllWithRole2()) {
            System.out.println(user);
        }


    }

    // 测试mybatis中的一级缓存
    @Test
    public void testOneCache() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user1 = userMapper.findById(1);
        System.out.println(user1);
        User user2 = userMapper.findById(1);
        System.out.println(user2);
        sqlSession.close();
    }


    // 测试mybatis中的二级缓存
    @Test
    public void testTwoCache() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);

        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        User user1 = userMapper1.findById(1);
        System.out.println(user1);
        User user2 = userMapper1.findById(1);
        System.out.println(user2);
        sqlSession1.close();
        System.out.println("**********************************************");
        user1 = userMapper2.findById(1);
        System.out.println(user1);
        user2 = userMapper2.findById(1);
        System.out.println(user2);
        sqlSession2.close();
    }
}
