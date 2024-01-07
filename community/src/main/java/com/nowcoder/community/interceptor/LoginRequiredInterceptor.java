package com.nowcoder.community.interceptor;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否是需要登录的方法(是否加上了@LoginRequired 注解)
        // 反射 handler是我们拦截的目标，也就是处理该request的controller中的某个方法
        // 万物皆对象，方法也是对象
        // Method是描述方法的类，一个具体的方法都是method的对象
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            LoginRequired annotation = method.getMethod().getAnnotation(LoginRequired.class);
            if (annotation != null) {
                // 需要登录
                User user = hostHolder.getUser();
                if (user == null) {
                    response.sendRedirect(request.getContextPath() + "/login");
                    log.info("debug...");
                    return false;
                }
            }
        }
        return true;
    }
}
