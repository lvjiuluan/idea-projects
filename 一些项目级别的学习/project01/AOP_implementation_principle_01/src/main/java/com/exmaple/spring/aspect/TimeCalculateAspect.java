package com.exmaple.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

public class TimeCalculateAspect {

    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("捕捉到的方法名：" + signature.getName());
    }

    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("捕捉到的方法名：" + signature.getName());
    }

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
