package com.lagou.task05_demo02;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "LifeFilter",initParams = {})
public class LifeFilter implements Filter {
    public LifeFilter() {
        System.out.println("构造方法执行！");
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("初始化操作正在火热进行中");
        System.out.println("获取到的过滤器名称为："+config.getFilterName());
//        config.getInitParameter()
    }

    public void destroy() {
        System.out.println("销毁操作执行完毕了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("阻止一切不合理的访问");
        // 放行操作
        chain.doFilter(request, response);
    }
}
