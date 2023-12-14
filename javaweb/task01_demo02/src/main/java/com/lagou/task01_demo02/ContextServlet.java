package com.lagou.task01_demo02;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;

public class ContextServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String s = initParameterNames.nextElement();
            System.out.println(s+"="+servletContext.getInitParameter(s));
        }

        System.out.println("-----------------------------------------------");

        // 相关路径的获取
        String contextPath = servletContext.getContextPath();
        System.out.println("获取上下文关联的路径信息为： "+contextPath);
        // 本质上就是获取工程路径 /工程名

        // '/'在服务器中被解析为 http://ip:port/工程名
        String realPath = servletContext.getRealPath("/");// 获取实际路径
        System.out.println("获取到的实际路径为："+realPath);
        //  获取到的是工程部署路径target，webapp目录拷贝过去的


        System.out.println("-----------------------------------------------");

        // 设置和获取属性信息
        servletContext.setAttribute("key","value");
        Object key = servletContext.getAttribute("key");
        System.out.println("根据参数指定的属性名获取到的属性为："+key);
        servletContext.removeAttribute("key");
        key = servletContext.getAttribute("key");
        System.out.println("根据参数指定的属性名获取到的属性为："+key);
    }
}
