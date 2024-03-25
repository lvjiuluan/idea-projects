package com.nowcoder.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * 一个controller类代表一个功能模块
 * 我把所有样例功能放到DemoController下
 *
 * */

@Controller
@RequestMapping("/demo")  // 这里和类名xxxController，表示访问该类下的方法
public class DemoController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "home";
    }
}
