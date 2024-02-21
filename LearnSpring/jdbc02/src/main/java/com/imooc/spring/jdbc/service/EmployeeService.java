package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;

import java.util.Date;

public class EmployeeService {

    private BatchService batchService;


    private EmployeeDao employeeDao;

    public void startImportJob() {
        batchService.importJob1();
        batchService.importJob2();
        System.out.println("批量导入成功！");
    }

    public void batchImport() {
        for (int i = 1; i <= 10; i++) {
            if (i == 3) {
                i = i / 0;
            }
            Employee employee = new Employee();
            employee.setEno(8000 + i);
            employee.setEname("员工" + i);
            employee.setSalary(4000f);
            employee.setDname("市场部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public BatchService getBatchService() {
        return batchService;
    }

    public void setBatchService(BatchService batchService) {
        this.batchService = batchService;
    }
}
