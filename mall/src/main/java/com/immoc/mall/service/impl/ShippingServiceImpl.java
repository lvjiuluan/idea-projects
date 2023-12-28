package com.immoc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.immoc.mall.dao.ShippingMapper;
import com.immoc.mall.pojo.Shipping;
import com.immoc.mall.service.IShippingService;
import com.immoc.mall.vo.ResponseVo;
import com.immoc.mall.form.ShippingAddOrUpdateForm;
import com.immoc.mall.vo.ShippingReturnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.immoc.mall.enums.ResponseEnum.*;

@Service
@Slf4j
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<ShippingReturnVo> add(Integer uid, ShippingAddOrUpdateForm shippingAddOrUpdateForm) {
        // 向数据库中插入一条shipping记录
        Shipping shipping = new Shipping();
        // 使用BeanUtils复制属性
        BeanUtils.copyProperties(shippingAddOrUpdateForm, shipping);
        shipping.setUserId(uid);
        log.info("shipping = {}", shipping);
        Integer i = shippingMapper.insertSelectiveId(shipping);
        if (i == 0) {
            return ResponseVo.error(ADD_SHIPPING_FAILURE);
        }
        // 插入成功
        // 把shippingId返回
        ShippingReturnVo shippingReturnVo = new ShippingReturnVo();
        shippingReturnVo.setShippingId(shipping.getId());
        return ResponseVo.sucess(shippingReturnVo, "新建地址成功");
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        // 根据shippingId删除收货地址
        // 先查询shippingId是否存在
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if (shipping == null) {
            return ResponseVo.error(SHIPPING_NOT_EXIST);
        }
        // 要删除的地址的user_id与当前登录用户的uid必须一致
        // 否则没有权限删除
        if (!shipping.getUserId().equals(uid)) {
            return ResponseVo.error(NO_RIGHT_DELETE_OR_UPDATE_SHIPPING);
        }
        int i = shippingMapper.deleteByPrimaryKey(shippingId);
        if (i == 0) {
            return ResponseVo.error(DELETE_SHIPPING_FAILURE);
        }
        return ResponseVo.sucessByMsg("删除地址成功");
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingAddOrUpdateForm shippingAddOrUpdateForm) {
        // 根据shippingId删除收货地址
        // 先查询shippingId是否存在
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if (shipping == null) {
            return ResponseVo.error(SHIPPING_NOT_EXIST);
        }
        // 要删除的地址的user_id与当前登录用户的uid必须一致
        // 否则没有权限修改
        if (!shipping.getUserId().equals(uid)) {
            return ResponseVo.error(NO_RIGHT_DELETE_OR_UPDATE_SHIPPING);
        }
        // 使用BeanUtils复制属性
        BeanUtils.copyProperties(shippingAddOrUpdateForm, shipping);
        shipping.setUpdateTime(null); // 这里的更新时间有数据库自己写
        int i = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (i == 0) {
            return ResponseVo.error(UPDATE_SHIPPING_FAILURE);
        }
        return ResponseVo.sucessByMsg("更新地址成功");
    }

    @Override
    public ResponseVo<PageInfo> queryAll(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(uid);
        PageInfo pageInfo = new PageInfo(shippingList);
        pageInfo.setList(shippingList);
        return ResponseVo.sucess(pageInfo);
    }
}
