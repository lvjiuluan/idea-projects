package com.immoc.mall.service;

import com.github.pagehelper.PageInfo;
import com.immoc.mall.vo.ResponseVo;
import com.immoc.mall.form.ShippingAddOrUpdateForm;
import com.immoc.mall.vo.ShippingReturnVo;

public interface IShippingService {
    /*
     * 1.添加地址
     * */
    ResponseVo<ShippingReturnVo> add(Integer uid, ShippingAddOrUpdateForm shippingAddOrUpdateForm);

    /*
     * 2.删除地址
     * */
    ResponseVo delete(Integer uid, Integer shippingId);

    /*
     * 3.更新地址
     * */
    ResponseVo update(Integer uid, Integer shippingId, ShippingAddOrUpdateForm shippingAddOrUpdateForm);

    /*
     * 4.分页查询本用户的所有收货地址
     * */
    ResponseVo<PageInfo> queryAll(Integer uid, Integer pageNum, Integer pageSize);
}
