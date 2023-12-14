package com.lagou.task02_demo1.servlet;

import com.lagou.task02_demo1.dao.UserDao;
import com.lagou.task02_demo1.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class RgisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 获取 HTTP请求中 保存的用户名和密码 信息
        request.setCharacterEncoding("utf-8");
        String userName = request.getParameter("userName");
        System.out.println("获取到的用户名为："+userName);
        String password = request.getParameter("password");
        System.out.println("获取到的密码为："+password);
        // 2 将用户名和密码打包成 对象(JavaBean)， 把这个对象扔给DAO层
        User user = new User(userName, password);
        UserDao userDao = new UserDao();
        int res = userDao.createUser(user);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        if(res !=0){
            writer.write("<h1>注册成功！</h1>");
        }else{
            writer.write("<h1>注册失败！</h1>");
        }
        writer.close();
    }
}
