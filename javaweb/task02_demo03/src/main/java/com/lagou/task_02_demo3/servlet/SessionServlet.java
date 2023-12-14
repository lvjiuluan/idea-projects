package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SessionServlet", value = "/session")
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建/获取 session对象
        HttpSession session = request.getSession();
        // 判断session是不是新创建的
        System.out.println(session.isNew()?"新创建的session对象":"已有的session对象");
        String id = session.getId();
        System.out.println("该session的id为："+id);

    }
}
