package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SessionServlet2", value = "/session2")
public class SessionServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // 1 设置属性名和属性值
        session.setAttribute("name","machao");
        // 2 指定属性名对应的属性值
        System.out.println("获取到的属性值为："+session.getAttribute("name"));
        // 3 删除指定的属性名
        session.removeAttribute("name");
        // 4 获取指定属性名对应的属性值
        System.out.println("获取到的属性值为："+session.getAttribute("name"));
    }
}
