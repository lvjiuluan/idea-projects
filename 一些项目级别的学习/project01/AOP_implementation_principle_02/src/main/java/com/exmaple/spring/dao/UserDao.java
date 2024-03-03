package com.exmaple.spring.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    public void insert() {
        System.out.println("插入了一条数据");
    }

    public void delete() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("删除了一条数据");
    }
}
