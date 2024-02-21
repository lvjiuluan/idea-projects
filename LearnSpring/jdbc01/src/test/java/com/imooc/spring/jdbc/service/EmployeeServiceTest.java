package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.SpringApplicationTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeServiceTest extends SpringApplicationTest {

    @Test
    public void bathImport() {
        employeeService.bathImport();
        System.out.println("批量导入成功");
    }
}