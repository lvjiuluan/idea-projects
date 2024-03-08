package com.example.spring.dao;


import com.example.spring.entity.Goods;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class GoodsDaoTest {

    @Test
    public void getById() {
        // 加载Mybatis配置文件
        InputStream is =
                GoodsDaoTest.class.getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取接口实现类对象
        GoodsDao goodsDao = sqlSession.getMapper(GoodsDao.class);
        Goods goods = goodsDao.getById(1);
        System.out.println(goods);
    }
}