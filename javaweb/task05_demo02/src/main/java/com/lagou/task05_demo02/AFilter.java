package com.lagou.task05_demo02;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "AFilter",value = "*.avi")
public class AFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("这是第一道防线");
        chain.doFilter(request, response);
        System.out.println("第一道防线返回");
    }
}
