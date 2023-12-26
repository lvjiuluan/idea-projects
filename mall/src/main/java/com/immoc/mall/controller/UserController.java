package com.immoc.mall.controller;

import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.form.UserForm;
import com.immoc.mall.pojo.User;
import com.immoc.mall.service.IUserService;
import com.immoc.mall.vo.ResponseVo;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.immoc.mall.enums.ResponseEnum.NEED_LOGIN;
import static com.immoc.mall.enums.ResponseEnum.PARAM_ERROR;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    /*@PostMapping("/register")
    // 前端用x-www-form-urlencoded方式
    public void register( User user) {
        log.info("username={}", user.getUsername());
    }*/
    @PostMapping("/register")
    // 前端用body里面的raw json格式
    public ResponseVo register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult) {
        // 判断所需字段是否为空
        if (bindingResult.hasErrors()) {
            log.error("提交注册的参数有误：{},{}", bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(PARAM_ERROR, bindingResult);
        }
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.register(user);
    }

}
