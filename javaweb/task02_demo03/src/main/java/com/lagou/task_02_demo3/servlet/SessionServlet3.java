package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SessionServlet3", value = "/session3")
public class SessionServlet3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 获取Session对象
        HttpSession session = request.getSession();
        // 2 获取Session对象的默认失效时间
        System.out.println("该Session对象的默认失效时间为: "+session.getMaxInactiveInterval()/60 + "分钟");
        // 3 设置失效时间
        session.setMaxInactiveInterval(1200);
        // 4 重新获取Session对象的失效时间
        System.out.println("该Session对象的失效时间为: "+session.getMaxInactiveInterval()/60 + "分钟");

    }
}
