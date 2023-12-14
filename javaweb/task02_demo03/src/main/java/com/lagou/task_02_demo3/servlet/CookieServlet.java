package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CookieServlet", value = "/cookie")
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("看看有没有执行到这里哦");
        // 创建cooki对象并添加到响应信息
        Cookie cookie = new Cookie("name", "zhangfei");
        response.addCookie(cookie);
        System.out.println("创建cookie成功！");
    }
}
