package com.lagou.proxy;

import com.lagou.service.AccountService;
import com.lagou.utils.TransactionManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 该类采用CGLIB动态代理方法来对目标类方法增强
 */
@Component
public class CglibProxyFactory {
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionManage transactionManage;

    // 生成代理对象
    public AccountService createAccountServiceJDKProxy() {
        // 编写CGLib对应的API来生成代理对象并放回
        AccountService accountServiceProxy = (AccountService) Enhancer.create(accountService.getClass(), new MethodInterceptor() {
            @Override
            /**
             * o
             * method
             * methodProxy
             */
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                // 开启事务管理器
                transactionManage.beginTransaction();

                try {
                    //  让被代理对象的原方法执行
                    result = method.invoke(accountService, objects);
                    // 手动提交事务
                    transactionManage.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    // 手动回滚事务
                    transactionManage.rollback();
                } finally {
                    // 手动释放资源
                    transactionManage.release();
                }
                return result;
            }
        });
        return accountServiceProxy;
    }
}
