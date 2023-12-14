package com.lagou.task05_demo01;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if ("admin".equals(userName) && "123456".equals(password)) {
            System.out.println("登陆成功");
            request.getSession().setAttribute("userName", userName);
            response.sendRedirect("main.jsp");
        } else {
            System.out.println("登陆失败");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
