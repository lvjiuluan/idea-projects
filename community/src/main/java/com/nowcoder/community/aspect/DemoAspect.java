package com.nowcoder.community.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class DemoAspect {
    /*
     * 第一个 * : 方法的返回值，什么返回值都可以
     * 第二个 * : service包下的所有类
     * 第三个 * : 所有的方法
     * (..): 表示所有的形参
     *
     * pointcut声明给哪些jointpoint加通知
     *
     * */
    @Pointcut("execution(* com.nowcoder.community.service.impl.*.*(..))")
    public void pointcut() {
    }

    /*
     * 定义通知
     * 针对的pointcut 具体的事情，以及具体的位置
     *
     * */
    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    // 有了返回值以后
    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("AfterThrowing");
    }

    /*
     * Around
     *即在前面，又再后面
     * 将目标方法包起来
     * */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around Start...");
        Object obj = joinPoint.proceed();
        System.out.println("Around End...");
        return obj;
    }
}
