package com.lagou.proxy;

import com.lagou.service.AccountService;
import com.lagou.service.impl.AccountServiceImpl;
import com.lagou.utils.TransactionManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理工厂类
 */
@Component
public class JDKProxyFactory {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionManage transactionManage;

    /**
     * 采用JDK动态代理技术生成目标类的代理对象
     * 返回值是目标类实现的接口类
     */
    public AccountService createAccountServiceJDKProxy() {
        AccountService accountServiceProxy = (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    /**
                     * proxy 当前代理对象的引用
                     * method 当前调用的目标方法所的引用
                     * args 方法的参数
                     */
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result = null;
                        // 开启事务管理器
                        transactionManage.beginTransaction();

                        try {
                            if (method.getName().equals("transfer")) {
                                System.out.println("进行了前置增强");
                                //  让被代理对象的原方法执行
                                result = method.invoke(accountService, args);
                                // 手动提交事务
                                transactionManage.commit();
                                System.out.println("进行了后置增强");
                            } else {
                                result = method.invoke(accountService, args);
                            }

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
