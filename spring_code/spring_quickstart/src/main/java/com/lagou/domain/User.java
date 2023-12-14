package com.lagou.domain;

import java.util.Map;

public class User {
    private String username;
    private Integer age;

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    private Map<String,Object> map;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
