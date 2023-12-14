package com.lagou.demo01;

import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CheckNameServlet", value = "/checkname")
public class CheckNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 接收参数
        String username = request.getParameter("username");
        Map<String,Object> map = new HashMap<String,Object>();
        // 业务处理
        if ("tom".equals(username)) {
            // 用户名存在
            map.put("flag",true);
            map.put("msg","用户名已经被占用");
        } else {
            // 用户名不存在
            map.put("flag",false);
            map.put("msg","用户名可以使用");
        }
        String result = JSON.toJSONString(map);
        System.out.println(result);
        response.getWriter().print(result);
    }
}
