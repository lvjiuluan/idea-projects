package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

public class EmployeeService {
    private EmployeeDao employeeDao;

    private DataSourceTransactionManager transactionManager;


    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void batchImport() {
        // 定义了事务默认的标准配置
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 开始一个事务，返回的是事务的状态，说明当前事务的执行阶段
        TransactionStatus status = transactionManager.getTransaction(definition);

        try {
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
            // 提交事务
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }

    }
}
