package com.example.springbootlearn;

import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/prefix")  //统一前缀
public class ParaController {
    @GetMapping("/first")
    public String firstRequest() {
        return "this a first spring-boot";
    }

    @GetMapping("/requestPara")
    public String requestPara(@RequestParam Integer num) {
        return "from request: " + num;
    }

    @GetMapping("/param/{num}")
    public String requestPath(@PathVariable Integer num) {
        return "from Path: " + num;
    }

    @GetMapping({"/multiUrl1", "/multiUrl2"})
    public String multiUrl(@RequestParam Integer num) {
        return "from Path: " + num;
    }

    @GetMapping("/required")
    public String required(@RequestParam(required = false, defaultValue = "0") Integer num) {
        return "from request: " + num;
    }
}
