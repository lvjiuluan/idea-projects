package com.nowcoder.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlphaController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
