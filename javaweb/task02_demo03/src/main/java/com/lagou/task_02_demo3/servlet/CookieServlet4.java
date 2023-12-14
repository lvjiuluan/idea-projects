package com.lagou.task_02_demo3.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CookieServlet4", value = "/cookie4")
public class CookieServlet4 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建cookie信息
        Cookie cookie = new Cookie("name", "liubei");
        // 获取改cookie默认的信息
        int maxAge = cookie.getMaxAge();
        System.out.println("该Cookie的默认使用期限是"+maxAge);
        // 修改cookie信息
        // 正：表示指定的秒数后失效
        // 负: 默认 (关闭浏览器后删除)
        // 0： 立即删除
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        System.out.println("改变cookie的声明周期成功！");
    }
}
