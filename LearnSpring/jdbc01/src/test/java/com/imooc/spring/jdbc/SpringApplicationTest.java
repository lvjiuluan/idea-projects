package com.imooc.spring.jdbc;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import com.imooc.spring.jdbc.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class SpringApplicationTest {
    @Autowired
    public EmployeeDao employeeDao;

    @Autowired
    public EmployeeService employeeService;

    @Test
    public void test() {
        Employee employee = employeeDao.findById(3308);
        System.out.println(employee);
    }
}