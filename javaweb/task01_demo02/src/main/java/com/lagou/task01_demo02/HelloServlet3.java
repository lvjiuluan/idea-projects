package com.lagou.task01_demo02;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet3 extends HttpServlet {
    public HelloServlet3() {
        System.out.println("构造方法开始咯");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("采用HttpServlet方式执行service");
    }

    @Override
    public void destroy() {
        System.out.println("销毁操作开始咯");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("初始化操作开始咯");
    }
}
