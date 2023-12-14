package com.lagou.dao;

//import com.lagou.com.lagou.impl.UserDaoImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.domain.User;
import com.lagou.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestMyBatis {

    /**
     * mybatis传统方式测试
     */
//    @Test
//    public void testfindAll() throws IOException {
//        // 调用持久层方法
//        IUserDao userDao = new UserDaoImpl();
//        List<User> users = userDao.findAll();
//        for (User user : users) {
//            System.out.println(user);
//        }
//
//    }

    /**
     * 利用mapper代理方式测试
     */
    @Test
    public void testfindUserById() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findUserById(1);
        System.out.println(user);
    }

    /**
     * 多条件查询方式 一
     */
    @Test
    public void findByIdAndUsername() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findByIdAndUsername(1, "%子%");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 多条件查询方式 二
     */
    @Test
    public void findByIdAndUsername2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findByIdAndUsername2(2, "应颠");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 多条件查询方式 三
     */
    @Test
    public void findByIdAndUsername3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(2);
        user.setUsername("应颠");
        List<User> users = mapper.findByIdAndUsername3(user);
        for (User u : users) {
            System.out.println(u);
        }
    }

    /**
     * 模糊查询方式 一
     */
    @Test
    public void findByUsername() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findByUsername("应%");
        for (User u : users) {
            System.out.println(u);
        }
    }

    /**
     * 模糊查询方式 二
     */
    @Test
    public void findByUsername2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findByUsername2("应%");
        for (User u : users) {
            System.out.println(u);
        }
    }

    /**
     * 添加用户 返回主键 方式一
     */
    @Test
    public void test8() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("某冰冰");
        user.setSex("女");
        user.setBirthday(new Date());
        user.setAddress("北京昌平");
        mapper.saveUser(user);
        System.out.println(user);
    }

    /**
     * 添加用户 返回主键 方式二
     */
    @Test
    public void test9() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("汤唯");
        user.setSex("女");
        user.setBirthday(new Date());
        user.setAddress("北京昌平");
        mapper.saveUser(user);
//        sqlSession.commit();
        this.testfindAll();
//        System.out.println(user);
    }

    /**
     * 动态sql之if 多条件查询
     */
    @Test
    public void test10() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("汤唯");
//        user.setId(12);
        List<User> users = mapper.findByIdAndUsernameIf(user);
        for (User user1 : users) {
            System.out.println(user1);
        }

    }

    /**
     * 查询所有用户
     */
    @Test
    public void testfindAll() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAll();
        for (User user1 : users) {
            System.out.println(user1);
        }

    }

    /**
     * 动态sql set 动态更新
     */
    @Test
    public void test11() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 业务
        User user = mapper.findUserById(11);
        user.setUsername("志明");
        mapper.updateIf(user);
        User newu = mapper.findUserById(11);
        System.out.println(newu);

    }

    /**
     * 动态sql for each 多值查询
     */
    @Test
    public void test12() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 业务
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(11);
        List<User> users = mapper.findByList(ids);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 动态sql for each 多值查询 数组
     */
    @Test
    public void test13() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 业务
        Integer[] ids = {1, 2, 11};
        List<User> users = mapper.findByArray(ids);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 核心配置文件深入： plugin标签   第三方插件pagehelper
     */
    @Test
    public void test14() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 对UserMapper接口产生代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 业务
        // 设置分页参数
        PageHelper.startPage(1,2);
        List<User> users = mapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        // 获取分页相关的其它参数
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        System.out.println("总条数："+userPageInfo.getTotal());
        System.out.println("总页数："+userPageInfo.getPages());
        System.out.println("判断当前是否是第一页："+userPageInfo.isIsFirstPage());
    }
}
