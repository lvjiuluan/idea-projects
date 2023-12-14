package com.lagou.task05_demo03;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class MySessonAttributeListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("属性创建");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("属性删除");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("属性修改");
    }
}
