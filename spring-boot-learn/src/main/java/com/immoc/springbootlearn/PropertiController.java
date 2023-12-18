package com.immoc.springbootlearn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *  演示读取配置Controller
 * */
@RestController
public class PropertiController {
    @Value("${shcool.grade}")  //注入
    Integer grade;
    @Value("${shcool.classNum}")  //注入
    Integer classNum;

    @GetMapping("/gradeClass")
    public String gradeClass() {
        return "年纪： " + grade + " 班级： " + classNum;
    }
}
