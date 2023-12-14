package com.lagou.demo01;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 1 获取请求数据
//        String username = request.getParameter("username");
//        // 2 模拟业务处理
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // 3 打印username
//        System.out.println(username);
//        response.getWriter().print("Hello World");
        String city_code = request.getParameter("city_code");
        URL url = new URL("http://t.weather.itboy.net/api/weather/city/"+city_code);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200){
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = reader.readLine()) != null){
                res.append(line);
            }
            reader.close();
            String s = res.toString();
            System.out.println(s);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(s);
        }
    }
}
