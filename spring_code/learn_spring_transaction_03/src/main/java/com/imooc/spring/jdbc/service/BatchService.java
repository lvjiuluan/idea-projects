package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class BatchService {
    @Autowired
    private EmployeeDao employeeDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
