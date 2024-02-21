package com.imooc.spring.jdbc.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {
    private Integer eno;
    private String ename;
    private Float salary;
    private String dname;
    private Date hiredate;
}
