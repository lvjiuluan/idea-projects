package com.lagou.reflect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class MyReflectDemo2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //利用反射机制与配置文件相结合，动态的创建对象，并调用方法
        // 1.读取配置文件信息
        Properties prop = new Properties();
        FileInputStream fileInputStream = new FileInputStream("jdbc_task02\\resources\\prop.properties");
        prop.load(fileInputStream);
        System.out.println(prop);
        String classname = prop.getProperty("classname");
        String method = prop.getProperty("method");
        System.out.println(classname);
        System.out.println(method);
        Class clazz = Class.forName(classname);
        Constructor con = clazz.getDeclaredConstructor(String.class, int.class, String.class);
        Object o = con.newInstance("张三", 21, "男");
        System.out.println(o);
        Method eat = clazz.getDeclaredMethod(method,String.class);
        eat.setAccessible(true);
        eat.invoke(o,"汉堡包");
        fileInputStream.close();
    }
}
