package com.nowcoder.community.exception;

import com.nowcoder.community.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 只去找Controller 的Bean进行处理
@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class ExceptionAdvice {
    // Exception: controller中发生的异常
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        log.error("服务器发生异常={}", e.getStackTrace());
        for (StackTraceElement element : e.getStackTrace()) {
            log.error(element.toString());
        }
        /*
         * 通过request判断是否为post请求
         * */
        String xRequestWith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestWith)) {
            // 该请求时post请求
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(0, "服务器异常"));
        } else {
            // 普通请求，重定向错误页面
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
