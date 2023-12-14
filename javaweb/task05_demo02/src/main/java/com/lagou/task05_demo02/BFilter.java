package com.lagou.task05_demo02;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "BFilter",value = "*.avi")
public class BFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("这是第二道防线");
        chain.doFilter(request, response);
        System.out.println("第二道防线返回");
    }
}
