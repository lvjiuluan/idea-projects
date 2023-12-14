package com.lagou.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration  //声明核心配置类
@ComponentScan("com.lagou") // 开启IOC注解扫描
@Import(DataSourceConfig.class) //导入数据库配置类
@EnableTransactionManagement // 开启事务注解支持
public class SpringConfig {
    @Bean //把当前方法的返回值对象放入IOC容器中，而该方法的调用是由Spring调用的
    public JdbcTemplate getJdbcTemplate(@Autowired DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @Bean //把当前方法的返回值对象放入IOC容器中，而该方法的调用是由Spring调用的
    public PlatformTransactionManager getPlatformTransactionManager(@Autowired DataSource dataSource) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }
}
