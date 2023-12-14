package com.lagou.dao.impl;

import com.lagou.dao.IUserDao;

import java.util.*;

public class UserDaoImpl implements IUserDao {
    private String username;
    private Integer age;

    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private Object[] array;

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    private Map<String,Object> map;
    public void setArray(Object[] array) {
        this.array = array;
    }

    private List<Object> list;

    public void setSet(Set<Object> set) {
        this.set = set;
    }

    private Set<Object> set;

    public void setList(List<Object> list) {
        this.list = list;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void save() {
//        System.out.println(username + age);
//        System.out.println("dao被调用了");
//        for (Object o : this.list) {
//            System.out.println(o);
//        }
//        for (Object o : this.set) {
//            System.out.println(o);
//        }
        System.out.println(this.list);
        System.out.println(this.set);
        System.out.println(Arrays.toString(array));
        System.out.println(map);
        System.out.println(properties);

    }
}
