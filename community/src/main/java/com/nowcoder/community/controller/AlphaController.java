package com.nowcoder.community.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/alpha")
public class AlphaController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello Spring Boot";
    }
}
