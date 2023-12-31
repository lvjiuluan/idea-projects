package com.immoc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.immoc.mall.form.OrderCreateFrom;
import com.immoc.mall.pojo.User;
import com.immoc.mall.service.impl.OrderServiceImpl;
import com.immoc.mall.vo.OrderVo;
import com.immoc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.immoc.mall.consts.MallConst.CURRENT_USER;

@RestController
@Slf4j
public class OderController {
    @Autowired
    private OrderServiceImpl orderService;

    // 1.创建订单
    @PostMapping("/orders")
    ResponseVo<OrderVo> create(@RequestBody OrderCreateFrom orderCreateFrom,
                               HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        Integer shippingId = orderCreateFrom.getShippingId();
        return orderService.create(uid, shippingId);
    }

    // 2.订单列表详情,分页查询
    @GetMapping("/orders")
    ResponseVo<PageInfo> list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              HttpSession httpSession) {

        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return orderService.list(uid, pageNum, pageSize);
    }

    // 3.根据订单编号返回订单详情, 只能查询自己的订单
    @GetMapping("/orders/{orderNo}")
    ResponseVo<OrderVo> detail(@PathVariable Long orderNo,
                               HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return orderService.detail(uid, orderNo);
    }

    // 4.取消订单
    @PutMapping("/orders/{orderNo}")
    ResponseVo cancel(@PathVariable Long orderNo,
                      HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return orderService.cancel(uid, orderNo);
    }
}
