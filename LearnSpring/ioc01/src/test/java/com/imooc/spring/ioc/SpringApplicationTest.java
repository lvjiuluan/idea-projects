package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Child;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class SpringApplicationTest extends TestCase {

    @Autowired
    private ApplicationContext context;

    @Test
    public void test01(){
        Child lily = context.getBean("lily", Child.class);
        lily.eat();
    }

}