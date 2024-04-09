package com.imooc.spring.jdbc.dao;


import com.imooc.spring.jdbc.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmployeeDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void findById() {
        Employee employee = employeeDao.findById(3308);
        System.out.println(employee);
    }

    @Test
    public void findByDname() {
        List<Employee> employeeList = employeeDao.findByDname("哇撒大");
        System.out.println(employeeList);
    }

    @Test
    public void findMapListByDname() {
        List<Map<String, Object>> mapList = employeeDao.findMapListByDname("哇撒大");
        for (Map<String, Object> map : mapList) {
            System.out.println(map);
        }
    }

    @Test
    public void insert() {
        Employee employee = new Employee();
        employee.setEno(3309);
        employee.setEname("Jack");
        employee.setSalary(5555.5f);
        employee.setDname("研发部");
        employee.setHiredate(new Date());
        int rows = employeeDao.insert(employee);
        System.out.println(rows);
    }

    @Test
    public void update() {
        Employee employee = employeeDao.findById(3309);
        employee.setSalary(employee.getSalary() + 1000);
        int rows = employeeDao.update(employee);
        System.out.println(rows);
    }

    @Test
    public void delete() {
        int rows = employeeDao.delete(3309);
        System.out.println(rows);
    }
}


/*
import com.imooc.spring.jdbc.entity.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmployeeDaoTest {
    @Test
    public void findById() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        EmployeeDao employeeDao = context.getBean("employeeDao", EmployeeDao.class);
        Employee employee = employeeDao.findById(3308);
        System.out.println(employee);
    }
}*/
