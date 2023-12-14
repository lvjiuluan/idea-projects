package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController // 组合了 @Controller 和@ResponseBody
@RequestMapping("/restful")
public class RestfulController {
    // 根据id查询
    // url = 项目名/restful/user/2
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @GetMapping("/user/{id}")
    public String findById(@PathVariable int id) {
        return "findById: " + id;
    }

    // 查询所有
    // url = 项目名/restful/user/
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @GetMapping("/user")
    public String findAll() {
        return "findAll: ";
    }

    // 新增
    @PostMapping("/user")
    public String post() {
        return "post";
    }

    // 更新
    @PutMapping("/user")
    public String put() {
        return "put";
    }

    // 删除
    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable int id) {
        return "delete：" + id;
    }
}
