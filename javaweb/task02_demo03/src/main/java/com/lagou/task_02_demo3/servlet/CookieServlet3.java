package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CookieServlet3", value = "/cookie3")
public class CookieServlet3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取客户端发来的cookie信息并打印
        Cookie[] cookies = request.getCookies();
//        System.out.println("获取到的cookie信息有");
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() +"="+cookie.getValue());
            if ("name".equalsIgnoreCase(cookie.getName())){
                cookie.setValue("guangyu");
                response.addCookie(cookie);
                break;
            }
        }
        System.out.println("修改cookie成功");
    }
}
