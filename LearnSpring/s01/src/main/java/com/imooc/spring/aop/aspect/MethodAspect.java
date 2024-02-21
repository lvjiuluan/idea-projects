package com.imooc.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

// 切面类
public class MethodAspect {
    // 切面方法,用于拓展额外的功能
    // joinPoint连接点, 通过连接点可以获取目标方法,目标类的信息
    public void printExecutionTime(JoinPoint joinPoint) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String now = sdf.format(new Date());
        // 利用joinPont可以获取目标方法
        // getTarget目标对象
        String className = joinPoint.getTarget().getClass().getName();
        // getSignature获取目标方法
        String methodName = joinPoint.getSignature().getName();
        System.out.println("-----> " + now + ":" + className + "." + methodName);
    }
}
