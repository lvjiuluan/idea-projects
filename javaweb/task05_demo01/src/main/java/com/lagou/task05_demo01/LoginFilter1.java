package com.lagou.task05_demo01;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/main.jsp")
public class LoginFilter1 implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object userName = request.getSession().getAttribute("userName");
        if (userName == null) {
//            request.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
            response.sendRedirect("login.jsp");
        } else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
