package com.lagou.dao;

public interface AccountDao {
    public void out(String outUser,double money);
    public void in(String inUser,double money);
}
