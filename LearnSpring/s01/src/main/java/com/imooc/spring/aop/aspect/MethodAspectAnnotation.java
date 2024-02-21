package com.imooc.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class MethodAspectAnnotation {
    @Pointcut("execution(public * com.imooc..*.*(..))")
    public void pointcut() {
    }

    /* @Before("pointcut()")
     public void printExecutionTime(JoinPoint joinPoint) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
         String now = sdf.format(new Date());
         String className = joinPoint.getTarget().getClass().getName();
         String methodName = joinPoint.getSignature().getName();
         System.out.print("-----> " + now + "执行了" + className + "." + methodName);
         *//*传入目标方法的实际参数*//*
        Object[] args = joinPoint.getArgs();
        System.out.println(" 方法参数:" + Arrays.asList(args));
    }*/
   /* @Before("pointcut()")
    public void before(){
        System.out.println("执行Before方法");
    }
    @AfterReturning("pointcut()")
    public void afterReturning(){
        System.out.println("执行AfterReturning方法");
    }
    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        System.out.println("执行AfterThrowing方法");
    }
    @After("pointcut()")
    public void After(){
        System.out.println("执行After方法");
    }
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        return result;
    }*/
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        /** ProceedingJointPoint时JointPoint的升级版
         * 只能用于Around通知
         * 可以控制目标方式是否执行
         **/
        Long startTime = new Date().getTime();
        Object result = joinPoint.proceed();
        Long endTime = new Date().getTime();
        long duration = endTime - startTime;
        if (duration >= 1000) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            String now = sdf.format(new Date());
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            System.out.print("-----> " + now + "执行执行时间过长("+duration+"ms)" + className + "." + methodName);
        }
        return result;
    }
    /*@AfterReturning(value = "pointcut()",returning = "result")
    public void afterReturning(Object result){
        System.out.println("执行AfterReturning方法, 返回值为"+result);

    }*/
}
