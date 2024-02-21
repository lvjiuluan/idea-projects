package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

/*
 * 编程式事务 == 手动式事务
 * */
public class EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    public void bathImport() {
        // 定义事务默认的标准配置
        TransactionDefinition definition = new DefaultTransactionDefinition();
        // 开始一个事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        // 该方法返回一个事务状态,说明当前事务的执行阶段
        System.out.println(status);
        try {
            for (int i = 0; i < 10; i++) {
                if (i == 4) {
                    i = i / 0;
                }
                Employee employee = new Employee();
                employee.setEno(8000 + i);
                employee.setEname("员工" + i);
                employee.setSalary(400f);
                employee.setDname("市场部");
                employee.setHiredate(new Date());
                employeeDao.insert(employee);
            }
        } catch (RuntimeException e) {
            // 事务回滚
            transactionManager.rollback(status);
            System.out.println(e + "," + status);
        }
        // 提交事务
        transactionManager.commit(status);
        System.out.println(status);
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }
}
