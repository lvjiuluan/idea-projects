package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;

import java.util.Date;

public class BatchService {
    private EmployeeDao employeeDao;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void importJob1() {
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setEno(33309 + i);
            employee.setEname("Jack" + i);
            employee.setSalary(5555.5f + i);
            employee.setDname("研发部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);

        }
    }

    public void importJob2() {
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setEno(43309 + i);
            employee.setEname("Mary" + i);
            employee.setSalary(5555.5f + i);
            employee.setDname("市场部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);

        }
    }
}
