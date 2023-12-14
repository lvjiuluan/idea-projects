package com.lagou.reflect;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MyReflectDemo1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Class clazz = Class.forName("com.lagou.reflect.Student");
        // 获取所有的方法对象，包含父类中所有的公告方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        System.out.println("*******************************");
        // 不能获取父类的公/私有方法，但是可以获取本类的私有方法
        Method[] methods1 = clazz.getDeclaredMethods();
        for (Method method : methods1) {
            System.out.println(method);
        }
        System.out.println("*******************************");
        // eat方法是私有的,只由declared才能找到
        Method eat = clazz.getDeclaredMethod("eat", String.class, int.class);
        System.out.println(eat);
        System.out.println(eat.getName());
        System.out.println(eat.getModifiers());
        System.out.println(eat.getReturnType());
        eat.setAccessible(true);
        int sa = (int) eat.invoke(new Student(), "sa", 1);
        System.out.println(sa);
        // 获取方法的形参
        for (Parameter parameter : eat.getParameters()) {
            System.out.println(parameter);
        }
        // 获取方法抛出的异常
        for (Class exceptionType : eat.getExceptionTypes()) {
            System.out.println(exceptionType);
        }
        saveObject(new Student("zhangsan",23,"男"));
        saveObject("sss");
    }

    // 把对象的所有信息存放到文件中
    public static void saveObject(Object obj) throws IllegalAccessException, IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./a.txt",true));
        // 获取字节码对象
        Class clazz = obj.getClass();
        // 获取成员变量
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            Object o = declaredField.get(obj);
//            bufferedWriter.write(name+"="+o);
//            bufferedWriter.newLine();
            bufferedWriter.append(name+"="+o);
            bufferedWriter.newLine();
            System.out.println(name+"="+o);
        }
        bufferedWriter.close();
    }
}
