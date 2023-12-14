package com.lagou.task02_demo2.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接收到了浏览器的请求");
        // 重定向http请求
//        response.sendRedirect("target.html");
        response.sendRedirect("https://www.baidu.com");
    }
}
