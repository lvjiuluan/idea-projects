package com.imooc.spring.ioc.bookshop.dao.impl;

import com.imooc.spring.ioc.bookshop.dao.IBookDao;

public class BookDaoOracleImpl implements IBookDao {
    @Override
    public void insert() {
        System.out.println("向Oracle Book表中插入一条数据");
    }
}
