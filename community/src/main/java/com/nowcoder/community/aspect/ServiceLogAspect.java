package com.nowcoder.community.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class ServiceLogAspect {
    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        /*
         * 用户[192.168.1.3], 在[2024-1-10]
         * 访问了 [com.nowcoder.community.service.Method]
         *
         * */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            /*
             * 不是从controller处调用service的话attributes会为空
             * */
            HttpServletRequest httpServletRequest = attributes.getRequest();
            String ip = httpServletRequest.getRemoteHost();
            String now = simpleDateFormat.format(new Date()).toString();
            String tatget = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            log.info("用户[{}]在[{}]访问了[{}]", ip, now, tatget);
        }

    }
}
