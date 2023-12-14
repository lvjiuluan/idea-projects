package com.lagou.task01_demo02;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
/*利用注解配置web.xml的信息*/
@WebServlet(name = "HelloServlet4",urlPatterns = "/hello4")
public class HelloServlet4 extends HttpServlet {
    /*@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("原来使用注解的方式如此潇洒");
        String method = req.getMethod();
        System.out.println("获取到的请求方式为: "+method);
        if("get".equalsIgnoreCase(method)){
            doGet(req,resp);
        }else if("post".equalsIgnoreCase(method)){
            doPost(req,resp);
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET请求方式");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST请求方式");
    }
}
