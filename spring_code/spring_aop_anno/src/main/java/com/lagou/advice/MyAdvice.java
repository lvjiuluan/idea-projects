package com.lagou.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect  // 升级为切面类：配置切入点和通知的关系
public class MyAdvice {
    // 抽取切点表达式
    @Pointcut("execution(* com.lagou.service.impl.AccountServiceImpl.*(..))")
    public void myPoint() {
    }

    // 使用before注解对 "" 这个切点进行增强
    @Before("MyAdvice.myPoint()")
    public void before() {
        System.out.println("前置方法执行了");
    }

    @AfterReturning("MyAdvice.myPoint()")
    public void afterReturning() {
        System.out.println("后置方法执行了");
    }

    @AfterThrowing("MyAdvice.myPoint()")
    public void afterThrowing() {
        System.out.println("异常通知执行了");
    }

    @After("MyAdvice.myPoint()")
    public void after() {
        System.out.println("最终通知执行了");
    }

    @Around("MyAdvice.myPoint()")
    public Object round(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            System.out.println("前置通知执行了");
            result = pjp.proceed();
            System.out.println("后置通知执行了");
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("异常通知执行了");
        } finally {
            System.out.println("最终通知执行了");
        }
        return result;
    }
}
