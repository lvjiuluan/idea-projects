package com.imooc.spring.ioc.bookshop.service;

import com.imooc.spring.ioc.bookshop.dao.IBookDao;

public class BookService {
    private IBookDao bookDao;

    public void purchase() {
        System.out.println("正在执行图书采购业务方法");
        bookDao.insert();
    }

    public void setBookDao(IBookDao bookDao) {
        this.bookDao = bookDao;
    }

    public IBookDao getBookDao() {
        return bookDao;
    }
}
