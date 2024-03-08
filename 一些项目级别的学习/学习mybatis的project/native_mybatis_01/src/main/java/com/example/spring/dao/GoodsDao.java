package com.example.spring.dao;

import com.example.spring.entity.Goods;

public interface GoodsDao {
    //    int updateGoodsUseCAS(Goods goods);
    Goods getById(int id);

    int updateGoodsUseCAS(Goods goods);
}
