package com.immoc.mall.controller;

import com.immoc.mall.form.UserRegisterForm;
import com.immoc.mall.form.UserLoginForm;
import com.immoc.mall.pojo.User;
import com.immoc.mall.service.IUserService;
import com.immoc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.immoc.mall.consts.MallConst.CURRENT_USER;
import static com.immoc.mall.enums.ResponseEnum.NEED_LOGIN;
import static com.immoc.mall.enums.ResponseEnum.PARAM_ERROR;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    /*@PostMapping("/register")
    // 前端用x-www-form-urlencoded方式
    public void register( User user) {
        log.info("username={}", user.getUsername());
    }*/
    @PostMapping("/user/register")
    // 前端用body里面的raw json格式
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm, BindingResult bindingResult) {
        // 判断所需字段是否为空
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(PARAM_ERROR, bindingResult);
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo login(@Valid @RequestBody UserLoginForm userLoginForm,
                            BindingResult bindingResult,
                            HttpSession session) {
        // 判断所需字段是否为空
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(PARAM_ERROR, bindingResult);
        }
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        // 设置Session
        session.setAttribute(CURRENT_USER, userResponseVo.getData());

        return userResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        if (user == null) {
            return ResponseVo.error(NEED_LOGIN);
        }
        return ResponseVo.sucess(user);
    }

}
