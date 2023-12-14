package com.lagou.testpool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
* 类的作用是创建代理示例
* */
public class ProxyUtil {
    /***
     * 方法的作用：给一个明星对象创建代理
     */
    // 利用多态的原理写返回值
    public static Star createProxy(BigStar bigStar){
        Star star = (Star) Proxy.newProxyInstance(
                ProxyUtil.class.getClassLoader(),
                new Class[]{Star.class}, //创建一个Class数组并初始化
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 参数一：代理的对象
                        // 参数二：要运行的方法 sing
                        // 参数三：调用sing方法时传入的实参
                        if ("sing".equals(method.getName())){
                            System.out.println("准备话筒，收钱");
                        } else if("dance".equals(method.getName())){
                            System.out.println("准备场地，收钱");
                        }
                        return method.invoke(bigStar,args);
                    }
                }
        );
        return star;
    }
}
