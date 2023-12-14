package com.lagou.servlet;

import com.lagou.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext classPathXmlApplicationContext = WebApplicationContextUtils.
                getWebApplicationContext(request.getSession().getServletContext());
        Account account = classPathXmlApplicationContext.getBean("account", Account.class);
        System.out.println(account);
    }
}
