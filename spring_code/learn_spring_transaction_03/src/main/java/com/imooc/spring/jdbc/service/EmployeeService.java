package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private BatchService batchService;

    public void batchImport() {
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            employee.setEno(3309 + i);
            employee.setEname("Jack" + i);
            employee.setSalary(5555.5f + i);
            employee.setDname("研发部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
            if (i == 5) {
                i = 1 / 0;
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void startImportJob() {
        batchService.importJob1();
        int i = 1 / 0;
        batchService.importJob2();
    }
}
