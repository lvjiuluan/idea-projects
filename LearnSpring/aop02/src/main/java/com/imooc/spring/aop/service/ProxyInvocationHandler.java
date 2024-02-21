package com.imooc.spring.aop.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProxyInvocationHandler implements InvocationHandler {
    private Object target;  //目标对象

    public void setTarget(Object target) {
        this.target = target;
    }

    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String now = sdf.format(new Date());
        System.out.println("=============" + now + "=============");
        Object result = method.invoke(target, args); // 调用目标对象的目标方法
        return result;
    }
}
