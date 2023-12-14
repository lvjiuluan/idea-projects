package com.lagou.testBeanUtils;

import com.lagou.pojo.Course;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TestBeanUtils {
    @Test
    public void test01() {
        // 1 创建Course对象
        Course course = new Course();
        // 2 创建Map
        Map<String, Object> map = new HashMap<String, Object>();
        // 3. map中添加数据
        //  key跟Course成员变量名保持一致，key与变量值保持一致
        map.put("id", 1);
        map.put("course_name", "大数据");
        map.put("brief", "课程包含所有大数据流行的技术");
        map.put("teacher_name", "周星星");
        map.put("teacher_info", "非著名演员");

        //  4 将map中的数据封装到course中
        try {
            BeanUtils.populate(course, map);
            System.out.println(course);
            BeanUtils.setProperty(course,"price",100.0);
            System.out.println(BeanUtils.getProperty(course,"price"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置属性

    }
}
