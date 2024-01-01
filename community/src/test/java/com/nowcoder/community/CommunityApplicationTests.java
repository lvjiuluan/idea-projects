package com.nowcoder.community;

import com.nowcoder.community.dao.AlphDao;
import com.nowcoder.community.dao.AlphaDaoHibernateImpl;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) // 以CommunityApplication为配置
public class CommunityApplicationTests implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testApplicationContext() {
        System.out.println("applicationContext = " + applicationContext);
        AlphDao alphDao = applicationContext.getBean(AlphDao.class);
        System.out.println(alphDao.select());
        alphDao = applicationContext.getBean("alphaHibernate", AlphDao.class);
        System.out.println(alphDao.select());
    }

    @Test
    public void testBeanManagement() {
        AlphaService alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println("alphaService = " + alphaService);
        AlphaService alphaService2 = applicationContext.getBean(AlphaService.class);
        System.out.println("alphaService = " + alphaService);
        System.out.println(alphaService == alphaService2);
    }
}
