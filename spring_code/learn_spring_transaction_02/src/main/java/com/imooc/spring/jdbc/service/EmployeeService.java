package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

public class EmployeeService {
    private EmployeeDao employeeDao;

    public BatchService getBatchService() {
        return batchService;
    }

    public void setBatchService(BatchService batchService) {
        this.batchService = batchService;
    }

    private BatchService batchService;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

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

    public void startImportJob() {
        batchService.importJob1();
        int i = 1 / 0;
        batchService.importJob2();
    }
}
