package com.lagou.task05_demo03;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineUser implements HttpSessionListener, ServletContextListener {
    // 使用ServletContext全局对象记录当前用户数量
    private ServletContext servletContext = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext = null;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("有新用户上线了");
        // 若当前用户为第一个用户
        Integer count = (Integer) servletContext.getAttribute("count");
        if (null == count) {
            // 设置用户数量为 0
            count = 0;
        }
        count++;
        servletContext.setAttribute("count",count);
        System.out.println("当前用户数量为："+servletContext.getAttribute("count"));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("有用户已下线...");
    }
}
