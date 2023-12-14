package com.lagou.task02_demo2.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TargetServlet", value = "/targetServlet")
public class TargetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("转发过来了");
        System.out.println(request.getAttribute("lvjiuluan"));
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<h1>转发成功！</h1>");
    }
}
