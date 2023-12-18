package com.immoc.springbootlearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 读取配置类
 * */
@RestController
public class ConfigController {
    @Autowired
    SchoolConfig schoolConfig;
    @GetMapping("/gradefromconfig")
    public String gradeClass() {
        return "年纪： " + schoolConfig.grade + " 班级： " + schoolConfig.classNum;
    }
}
