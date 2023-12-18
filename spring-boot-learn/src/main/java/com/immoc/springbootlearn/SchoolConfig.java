package com.immoc.springbootlearn;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * School 配置类
 * */
@Component
@ConfigurationProperties(prefix = "shcool")
public class SchoolConfig {
    Integer grade;
    Integer classNum;


    /**
     * 获取
     *
     * @return grade
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * 设置
     *
     * @param grade
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取
     *
     * @return classNum
     */
    public Integer getClassNum() {
        return classNum;
    }

    /**
     * 设置
     *
     * @param classNum
     */
    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }
}
