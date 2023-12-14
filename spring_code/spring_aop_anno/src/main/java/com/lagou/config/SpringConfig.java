package com.lagou.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration // 表面这是核心配置类
@ComponentScan("com.lagou") //开启IOC注解扫描
@EnableAspectJAutoProxy  // 开启AOP自动代理
public class SpringConfig {
}
