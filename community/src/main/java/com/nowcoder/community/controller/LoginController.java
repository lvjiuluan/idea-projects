package com.nowcoder.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    // 1 处理访问注册页面的请求
    @GetMapping("/register")
    public String getRegisterPage() {
        return "site/register";
    }
    // 2 提交信息进行注册的请求
//    @PostMapping
}
