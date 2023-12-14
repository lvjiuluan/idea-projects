package com.lagou.task01_demo02;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

public class ConfigServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("初始化操作执行了");
        /*
        *  <servlet-name>ConfigServlet</servlet-name>
        * */
        System.out.println("Servlet的别名是："+servletConfig.getServletName());
        System.out.println("--------------------------------------------------");
        // 获取配置文件中的初始化参数信息
        String username = servletConfig.getInitParameter("username");
        System.out.println("获取到的初始化用户名为："+username);
        // 获取所有配置参数的名称
        Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            System.out.println("初始化参数名为："+initParameterNames.nextElement());
        }
        System.out.println("--------------------------------------------------");
        // 获取servletContex接口的引用
        ServletContext servletContext = servletConfig.getServletContext();
        System.out.println("获取到的servleContext引用为："+servletContext);
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
