package com.lagou.base;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class BaseServlet extends HttpServlet {
    /***
     * doGet方法作为一个调度器，根据请求功能的不同，调用对应的方法
     * 规定必须传递一个参数
     *          methodName=功能名
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 获取参数，要访问的方法名
//        String methodName = req.getParameter("methodName");
        String methodName = null;
        // a 获取Post请求的ContentType类型
        String contentType = req.getHeader("Content-Type");
        // b 判断传递的数据是不是JSON格式
        if ("application/json;charset=utf-8".equalsIgnoreCase(contentType)) {
            // 证明是json格式数据
            String postJson = getPostJson(req);
            Map<String, Object> map = JSON.parseObject(postJson, Map.class);
            // 从map中取出methodName
            methodName = (String) map.get("methodName");
            // 将获取到的数据保存到request域对象
            // 声明周期为 一次请求
            req.setAttribute("map",map);
        } else {
            methodName = req.getParameter("methodName");
        }
        // 2 判断执行对应的方法
      /*  if ("addCourse".equals(methodName)) {
            addCourse(req, resp);
        } else if ("findByName".equals(methodName)) {
            findByName(req, resp);
        } else if ("findByStatus".equals(methodName)) {
            findByStatus(req, resp);
        } else {
            System.out.println("请求功能不存在！！");
        }*/
        // 2.1 利用反射机制优化代码
        if (methodName != null) {
            // a 获取当前类的字节码文件对象
            Class clazz = this.getClass();
            // b 根据传入的方法名获取对应的方法对象
            try {
                Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                // c 调用method对象的invoke方法，执行对应功能
                method.invoke(this, req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("请求的功能不存在！！");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 从json请求中获取参数名
    public String getPostJson(HttpServletRequest request) {
        try {
            // 1 从request中获取缓冲输入流对象
            BufferedReader reader = request.getReader();
            // 2 创建StringBuffer 保存读取出的数据
            StringBuffer sb = new StringBuffer();
            // 3 循环读取
            String line = null;
            while ((line = reader.readLine()) != null) {
                // 将每次读取的数据追加到StringBuffer中
                sb.append(line);
            }
            // 4 返回结果
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
