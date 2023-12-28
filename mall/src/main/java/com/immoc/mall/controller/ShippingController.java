package com.immoc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.immoc.mall.pojo.User;
import com.immoc.mall.service.impl.ShippingServiceImpl;
import com.immoc.mall.vo.ResponseVo;
import com.immoc.mall.form.ShippingAddOrUpdateForm;
import com.immoc.mall.vo.ShippingReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.immoc.mall.consts.MallConst.CURRENT_USER;

@RestController
public class ShippingController {
    @Autowired
    private ShippingServiceImpl shippingService;

    // 1.添加地址
    @PostMapping("/shippings")
    public ResponseVo<ShippingReturnVo> add(@Valid @RequestBody ShippingAddOrUpdateForm shippingAddOrUpdateForm,
                                            HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return shippingService.add(uid, shippingAddOrUpdateForm);
    }

    // 删除地址
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,
                             HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return shippingService.delete(uid, shippingId);
    }

    // 修改地址
    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingAddOrUpdateForm shippingAddOrUpdateForm,
                             HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return shippingService.update(uid, shippingId, shippingAddOrUpdateForm);
    }

    // 分页查询地址
    @GetMapping("/shippings")
    public ResponseVo<PageInfo> queryAll(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return shippingService.queryAll(uid, pageNum, pageSize);
    }
}
