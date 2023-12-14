package com.lagou.reflect;

import java.lang.reflect.Field;

public class MyReflectDemo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 1.获取class字节码文件对象
        Class clazz = Class.forName("com.lagou.reflect.Student");
        // 2.获取成员变量的对象
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        Field[] fields2 = clazz.getDeclaredFields();
        for (Field field : fields2) {
            System.out.println(field);
        }
        Field gender = clazz.getField("gender");
        System.out.println(gender);
        Field name = clazz.getDeclaredField("name");
        System.out.println(name);
        // 获取权限修饰符
        int modifiers = name.getModifiers();
        System.out.println(modifiers);
        System.out.println(name.getName());
        System.out.println(name.getType());
        // 获取成员变量的值
        Student student = new Student("zhangsan", 23, "man");
        name.setAccessible(true); //临时取消访问权限
        System.out.println(name.get(student));
        // 修改对象记录的值
        name.set(student,"wangwu");
        System.out.println(student);
    }
}
