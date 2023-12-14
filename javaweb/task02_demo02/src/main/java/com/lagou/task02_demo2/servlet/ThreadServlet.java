package com.lagou.task02_demo2.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ThreadServlet", value = "/thread")
public class ThreadServlet extends HttpServlet {
    private String name; // 准备一个成员变量，作为一个共享资源
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*synchronized (this) {
            // 获取request对象中参数名为name的值
            // 将name值赋值给成员变量name
            // 线程沉睡5秒
            // 将name数值响应给浏览器
            String name = request.getParameter("name");
            System.out.println("获取到的name数值为："+name);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PrintWriter writer = response.getWriter();
            writer.write("<h1>"+ name +"</h1>");
            writer.close();
        }*/

        // 获取request对象中参数名为name的值
        // 将name值赋值给成员变量name
        // 线程沉睡5秒
        // 将name数值响应给浏览器
        name = request.getParameter("name");
        System.out.println("获取到的name数值为："+name);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PrintWriter writer = response.getWriter();
        writer.write("<h1>"+ name +"</h1>");
        writer.close();
    }
}
