package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CookieServlet5", value = "/cookie5")
public class CookieServlet5 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建cookie对象并赋值
//        Cookie cookie = new Cookie("name", "zhaoyun");
        Cookie cookie = new Cookie("name", "huangzhong");
        // 修改Cookie的路径信息
        cookie.setPath(request.getContextPath()+"/hello");
        // 添加到响应信息
        response.addCookie(cookie);
        System.out.println("设置Cookie路径成功");
    }
}
