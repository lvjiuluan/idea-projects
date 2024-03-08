package com.example.spring.dao;


import com.example.spring.entity.Goods;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class GoodsDaoTest {
    private GoodsDao goodsDao;
    private SqlSession sqlSession;

    @Before
    public void init() {
        // 加载Mybatis配置文件
        InputStream is = GoodsDaoTest.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 获取sqlSession
        sqlSession = sqlSessionFactory.openSession();
        // 获取接口实现类对象
        goodsDao = sqlSession.getMapper(GoodsDao.class);
    }

    @Test
    public void updateGoodsUseCAS() {
        // 获取商品
        Goods good1 = goodsDao.getById(1);
        Goods good2 = goodsDao.getById(1);

        System.out.println(good1 == good2);

        // 打印商品信息
        System.out.println("A操作员获取的good: " + good1);
        System.out.println("B操作员获取的good: " + good2);

        // 修改商品
        good1.setStatus(2);
        good2.setStatus(2);

        System.out.println("A操作员操作后的good: " + good1);
        System.out.println("B操作员操作后的good: " + good2);


        // A先提交，B后提交

        int update1 = goodsDao.updateGoodsUseCAS(good1);

        System.out.println("A操作员" + (update1 == 1 ? "成功" : "失败"));

        int update2 = goodsDao.updateGoodsUseCAS(good2);
        System.out.println("B操作员" + (update2 == 1 ? "成功" : "失败"));

        sqlSession.commit();

    }
}