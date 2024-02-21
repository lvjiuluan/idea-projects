package com.imooc.spring.jdbc.dao;

import com.imooc.spring.jdbc.SpringApplicationTest;
import com.imooc.spring.jdbc.entity.Employee;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

public class EmployeeDaoTest extends SpringApplicationTest {

    int eno = 3308;

    @Test
    public void findById() {
        Employee employee = employeeDao.findById(eno);
        System.out.println(employee);
    }

    @Test
    public void findByDname() {
        for (Employee employee : employeeDao.findByDname("研发部")) {
            System.out.println(employee);
        }

    }

    @Test
    public void findMapByDname() {
        for (Map<String, Object> map : employeeDao.findMapByDname("研发部")) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

        }
    }

    @Test
    public void insert() {
        Employee employee = new Employee();
        employee.setEno(8888);
        employee.setEname("赵六");
        employee.setSalary(6666f);
        employee.setDname("研发部");
        employee.setHiredate(new Date());
        employeeDao.insert(employee);
//        eno = 8888;
//        findById();
    }

    @Test
    public void update() {
        Employee employee = employeeDao.findById(8888);
        employee.setDname("puj");
        employeeDao.update(employee);
        System.out.println(employeeDao.findById(8888));
    }

    @Test
    public void delete() {
        employeeDao.delete(8888);
    }
}