package com.imooc.spring.jdbc.dao;

import com.imooc.spring.jdbc.entity.Employee;
import lombok.Data;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Data
public class EmployeeDao {
    private JdbcTemplate jdbcTemplate;

    public Employee findById(Integer eno) {
        String sql = "select * from employee where eno = ?";
        Employee employee = jdbcTemplate.
                queryForObject(sql,
                        new Object[]{eno},
                        new BeanPropertyRowMapper<>(Employee.class));
        return employee;
    }

    public List<Employee> findByDname(String dname) {
        String sql = "select * from employee where dname = ?";
        List<Employee> employees = jdbcTemplate.query(
                sql,
                new Object[]{dname},
                new BeanPropertyRowMapper<>(Employee.class)
        );
        return employees;
    }

    public List<Map<String, Object>> findMapByDname(String dname) {
        String sql = "select eno as empno,salary as s from employee where dname = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(
                sql,
                new Object[]{dname}
        );
        return maps;
    }

    public void insert(Employee employee) {
        String sql = "insert into employee(eno,ename,salary,dname,hiredate) values(?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                new Object[]{employee.getEno(), employee.getEname(),
                        employee.getSalary(), employee.getDname(), employee.getHiredate()}
        );
    }

    public void update(Employee employee) {
        String sql = "update employee set ename=?,salary=?,dname=?,hiredate=? where eno = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{employee.getEname(),
                        employee.getSalary(), employee.getDname(), employee.getHiredate(),
                        employee.getEno()}
        );
    }

    public void delete(int eno) {
        String sql = "delete from employee where eno = ?";
        jdbcTemplate.update(
                sql,
                new Object[]{eno}
        );
    }
}
