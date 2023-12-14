package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CookieServlet2", value = "/cookie2")
public class CookieServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取客户端发来的cookie信息并打印
        Cookie[] cookies = request.getCookies();
        System.out.println("获取到的cookie信息有");
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() +"="+cookie.getValue());
        }
    }
}
