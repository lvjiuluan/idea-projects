package com.lagou.task05_demo03;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

public class Student implements Serializable, HttpSessionActivationListener {
    private String name;

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("执行了钝化操作"+se.getSession());
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println("执行了活化操作"+se.getSession());
    }

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Student{name = " + name + "}";
    }
}
