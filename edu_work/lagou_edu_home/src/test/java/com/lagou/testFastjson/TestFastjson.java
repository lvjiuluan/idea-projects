package com.lagou.testFastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestFastjson {
    @Test
    public void javaBeanJson(){
        // 1 创建Person对象
        Person p = new Person("小斌", 25, DateUtils.getDateFormart());
        // 2 使用json对象 将Person对象转换为json数据
        String s = JSON.toJSONString(p);
        System.out.println(s);
    }
    @Test
    public void javaListToJson(){
        Person p1 = new Person("小斌1", 25, DateUtils.getDateFormart());
        Person p2 = new Person("小斌2", 25, DateUtils.getDateFormart());
        Person p3 = new Person("小斌3", 25, DateUtils.getDateFormart());
        ArrayList<Person> list = new ArrayList<>();
        Collections.addAll(list,p1,p2,p3);
        String s = JSON.toJSONString(list);
        System.out.println(s);
    }
    @Test
    public void jsonToJavaBean(){
        String s = "{\"USERNAME\":\"小斌1\",\"AGE\":25,\"birthday\":\"2023-07-07 18:15:14\"}";
        Person person = JSON.parseObject(s, Person.class);
        System.out.println(person);
    }
    @Test
    public void jsonToList(){
        String s = "[{\"USERNAME\":\"小斌1\",\"AGE\":25,\"birthday\":\"2023-07-07 18:18:01\"},{\"USERNAME\":\"小斌2\",\"AGE\":25,\"birthday\":\"2023-07-07 18:18:01\"},{\"USERNAME\":\"小斌3\",\"AGE\":25,\"birthday\":\"2023-07-07 18:18:01\"}]";
        List<Person> people = JSON.parseArray(s, Person.class);
        System.out.println(people);
    }
}
