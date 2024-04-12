package com.hhx.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: hhx
 * @Date: 2024/4/12 10:55
 * @Description: TODO
 * @Version: 1.0
 */
@Controller
public class HelloController {
    // "/"->WEB-INF/template/index.html
    @RequestMapping(value = "/")
    public String index() {
        //返回视图名称
        return "index";
    }

    @RequestMapping("/target")
    public String toTarget() {
        return "target";
    }
}
