package com.lagou.entity;

import java.io.Serializable;
import java.util.Date;

/***
 * JavaBean 类
 * 用来存储数据：私有化成员变量；提供空参构造；提供set,get成员方法；实现序列化接口
 */
public class Employee implements Serializable {
    private int eid;
    private String ename;
    private int age;
    private String sex;
    private double salary;
    private Date empdate;

    public Employee() {
    }

    public Employee(int eid, String ename, int age, String sex, double salary, Date empdate) {
        this.eid = eid;
        this.ename = ename;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
        this.empdate = empdate;
    }

    /**
     * 获取
     * @return eid
     */
    public int getEid() {
        return eid;
    }

    /**
     * 设置
     * @param eid
     */
    public void setEid(int eid) {
        this.eid = eid;
    }

    /**
     * 获取
     * @return ename
     */
    public String getEname() {
        return ename;
    }

    /**
     * 设置
     * @param ename
     */
    public void setEname(String ename) {
        this.ename = ename;
    }

    /**
     * 获取
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取
     * @return salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * 设置
     * @param salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * 获取
     * @return empdate
     */
    public Date getEmpdate() {
        return empdate;
    }

    /**
     * 设置
     * @param empdate
     */
    public void setEmpdate(Date empdate) {
        this.empdate = empdate;
    }

    public String toString() {
        return "Employee{eid = " + eid + ", ename = " + ename + ", age = " + age + ", sex = " + sex + ", salary = " + salary + ", empdate = " + empdate + "}";
    }
}
