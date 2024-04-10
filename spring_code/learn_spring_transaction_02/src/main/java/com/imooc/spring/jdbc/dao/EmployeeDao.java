package com.imooc.spring.jdbc.dao;

import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Employee findById(Integer eno) {
        String sql = "select * from employee where eno = ?";
        // 查询单条数据
        Employee employee = jdbcTemplate.queryForObject(sql, new Object[]{eno}, new BeanPropertyRowMapper<>(Employee.class));
        return employee;
    }

    public List<Employee> findByDname(String dname) {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "select * from employee where dname = ?";
        // 查询复合数据
        employeeList = jdbcTemplate.query(sql, new Object[]{dname}, new BeanPropertyRowMapper<>(Employee.class));
        return employeeList;
    }

    public List<Map<String, Object>> findMapListByDname(String dname) {
        String sql = "select * from employee where dname = ?";
        // 查询复合数据
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{dname});
        // 查询到的结果一个map就是一行数据，有多行数据，所有有多个map，组成map list
        return mapList;
    }

    public int insert(Employee employee) {
        String sql = "insert into employee values(?,?,?,?,?)";
        Object[] args = new Object[]{employee.getEno(), employee.getEname(), employee.getSalary(), employee.getDname(), employee.getHiredate()};
        int rows = jdbcTemplate.update(sql, args);
        return rows;
    }

    public int update(Employee employee) {
        String sql = "update employee set ename = ?, salary = ?, dname = ?, hiredate = ? where eno = ?";
        Object[] args = new Object[]{employee.getEname(), employee.getSalary(), employee.getDname(), employee.getHiredate(), employee.getEno()};
        int rows = jdbcTemplate.update(sql, args);
        return rows;
    }

    public int delete(Integer eno) {
        String sql = "delete from employee where eno = ?";
        int rows = jdbcTemplate.update(sql, new Object[]{eno});
        return rows;
    }
}
