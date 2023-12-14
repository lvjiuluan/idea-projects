package com.lagou.advice;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 通知类
 */
public class MyAdvice {
    public void before() {
        System.out.println("前置通知执行了");
    }

    public void afterReturning() {
        System.out.println("后置通知执行了");
    }

    public void afterThrowing() {
        System.out.println("异常通知执行了");
    }

    public void after() {
        System.out.println("最终通知执行了");
    }

    /*
       pjp 切点
     */
    public Object around(ProceedingJoinPoint pjp) {
        Object proceed = null;
        try {
            // 切点方法执行
            System.out.println("前置通知执行了");
            proceed = pjp.proceed();
            System.out.println("后置通知执行了");
        } catch (Throwable e) {
            System.out.println("异常通知执行了");
        } finally {
            System.out.println("最终通知执行了");
        }
        return null;
    }
}
