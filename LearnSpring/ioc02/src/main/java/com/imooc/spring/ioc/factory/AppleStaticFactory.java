package com.imooc.spring.ioc.factory;

import com.imooc.spring.ioc.entity.Apple;

public class AppleStaticFactory {


    public static Apple createSweetApple() {

        Apple apple = new Apple();
        apple.setTittle("红富士");
        apple.setOriginal("欧洲");
        apple.setColor("红色");
        return apple;
    }
}
