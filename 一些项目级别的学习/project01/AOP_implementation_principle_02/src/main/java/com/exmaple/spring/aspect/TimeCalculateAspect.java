package com.exmaple.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
//@EnableAspectJAutoProxy
public class TimeCalculateAspect {

    @Pointcut("execution(* com.exmaple.spring.service.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("捕捉到的方法名：" + signature.getName());
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("捕捉到的方法名：" + signature.getName());
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            System.out.println(joinPoint.getSignature().getName()
                    + "消耗时间: " + (endTime - startTime) + "毫秒");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
}
