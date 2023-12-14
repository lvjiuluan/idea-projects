package com.lagou.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfig {
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /*
        <context:component-scan base-package="com.lagou"></context:component-scan>
        <!--    类路径：指的是当前项目编译过后的classes目录-->
        <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>
        <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource">
            <property name="driverClassName" value="${jdbc.driverClassName}"></property>
            <property name="url" value="${jdbc.url}"></property>
            <property name="username" value="${jdbc.username}"></property>
            <property name="password" value="${jdbc.password}"></property>
        </bean>
        <bean class="org.apache.commons.dbutils.QueryRunner" id="queryRunner">
            <constructor-arg ref="dataSource" name="ds"></constructor-arg>
        </bean>
    */
    @Bean("dataSource") //srping为把<“dataSource”,dataSource>注入到IOC MAP 中
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
