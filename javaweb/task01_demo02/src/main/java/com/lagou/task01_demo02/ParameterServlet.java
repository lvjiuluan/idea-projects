package com.lagou.task01_demo02;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;

public class ParameterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接受数据之前，将request中的数据设置请求数据中的编码方式
        request.setCharacterEncoding("utf-8");
        // 1 获取指定参数名称的值并打印
        String name = request.getParameter("name");
        System.out.println("获取到的姓名为："+name);
        String[] hobbies = request.getParameterValues("hobby");
        System.out.print("获取到的爱好有：");
        for (String hobby : hobbies) {
            System.out.print(hobby+" ");
        }
        System.out.println();
        System.out.println("------------------------------------------");
        // 2 获取所有参数的名称
        Enumeration<String> parameterNames = request.getParameterNames();
        System.out.print("获取到的所有参数名称为：");
        while (parameterNames.hasMoreElements()){
            System.out.print(parameterNames.nextElement()+" ");
        }
        System.out.println();
        System.out.println("------------------------------------------");
        // 3 获取请求参数的键值对
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            System.out.print("参数名："+s+"=");
            for (String ss : parameterMap.get(s)){
                System.out.print(ss+" ");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------");
        // 4 获取客户端请求的其他信息
        System.out.println("发送HTTP请求的客户端的IP地址为："+request.getRemoteAddr());
        System.out.println("发送HTTP请求的客户端的端口号为："+request.getRemotePort());
        System.out.println("请求资源路径为："+request.getRequestURI());
        System.out.println("请求资源路径的完整路径为："+request.getRequestURL());
        System.out.println("请求方式为："+request.getMethod());
        System.out.println("请求附带的参数为："+request.getQueryString());
        System.out.println("请求的Servlet路径为："+request.getServletPath());

        System.out.println("------------------------------------------");
        // 5 服务器向浏览器发出响应数据

        // 5.1 获取响应数据的默认编码方式
        String characterEncoding = response.getCharacterEncoding();
        System.out.println("服务器响应数据的默认编码方式为："+characterEncoding);
        // 在方发送数据之前设置服务器和浏览器的编码方式以及文本类型
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("修改后服务器响应数据的编码方式为："+response.getCharacterEncoding());
        // 上面5.1节的代码必须在构造writer对象之前！！！

        PrintWriter writer = response.getWriter();
//        writer.write("我接受到了！");
        Random ra = new Random();
        int i = ra.nextInt(100) + 1;
        writer.write("<h1>"+i+"</h1>");
        System.out.println("服务器发送数据成功！");
        writer.close();
    }
}
