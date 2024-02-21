package com.imooc.spring.ioc.factory;

import com.imooc.spring.ioc.entity.Apple;

public class AppleInstanceFactory {
    public AppleInstanceFactory() {
        System.out.println("实例化工厂被Spring IoC创建");
    }

    public Apple createSweetApple() {
        System.out.println("实例化工厂创建了苹果");
        Apple apple = new Apple();
        apple.setTittle("红富士");
        apple.setOriginal("欧洲");
        apple.setColor("红色");
        return apple;
    }
}
