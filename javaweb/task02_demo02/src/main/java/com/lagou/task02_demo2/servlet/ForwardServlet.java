package com.lagou.task02_demo2.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ForwardServlet", value = "/forwardServlet")
public class ForwardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接受到浏览器的请求");
        // 转发，让其它web组件处理请求
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/targetServlet");
        request.setAttribute("lvjiuluan","22");
        requestDispatcher.forward(request,response);
    }
}
