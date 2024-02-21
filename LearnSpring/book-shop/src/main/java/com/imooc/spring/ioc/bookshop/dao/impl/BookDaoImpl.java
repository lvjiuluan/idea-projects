package com.imooc.spring.ioc.bookshop.dao.impl;

import com.imooc.spring.ioc.bookshop.dao.IBookDao;

public class BookDaoImpl implements IBookDao {
    @Override
    public void insert() {
        System.out.println("向MySql Book表中插入了一条数据");
    }
}
