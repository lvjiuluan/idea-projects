package com.imooc.spring.ioc;

import com.imooc.spring.ioc.config.ApplicationContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContextConfig.class)
public class SpringApplicationJavaConfigTest {
    @Value("${connection.password}")
    private String password;

    @Test
    public void test01(){
        System.out.println(password);
    }
}