package com.lagou.testpool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class MyRflectDemo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 1.Class.forName方法
        Class clazz1 = Class.forName("com.lagou.testpool.Student");
        System.out.println(clazz1);
        // 2.类名.class
        Class clazz2 = Student.class;
        System.out.println(clazz2);
        // 3.对象.getClass
        Student student = new Student();
        Class clazz3 = student.getClass();
        System.out.println(clazz1 == clazz3);

        // 获取构造方法
        Constructor[] cons = clazz1.getConstructors();
        for (Constructor con : cons) {
            System.out.println(con);
        }
        Constructor[] cons2 = clazz1.getDeclaredConstructors();
        for (Constructor con : cons2) {
            System.out.println(con);
        }
        System.out.println(clazz1.getConstructor());
        System.out.println(clazz1.getDeclaredConstructor(String.class));
        System.out.println(clazz1.getDeclaredConstructor(int.class));
        System.out.println(clazz1.getDeclaredConstructor(String.class,int.class));

        Constructor con = clazz1.getDeclaredConstructor(String.class, int.class);
        int modifiers = con.getModifiers(); // 获取权限修饰符，整数
        System.out.println(modifiers);
        Parameter[] parameters = con.getParameters();
        for (Parameter parameter : parameters){
            System.out.println(parameter);
        }
        // 暴力反射，就算时私有构造函数也可以创建对象，取消权限校验
        con.setAccessible(true);
        Student s = (Student) con.newInstance("s", 21);
        System.out.println(s);
    }

}
