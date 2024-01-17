package com.nowcoder.community.config;

import com.nowcoder.community.interceptor.LoginInterceptor;
import com.nowcoder.community.interceptor.LoginRequiredInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

//    @Autowired
//    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor);
        /*
        * server:
              port: 8080
              servlet:
                context-path: /community
        * */
        // 这里的patterns以context-path为起点
        // 这里应该何controller那里的url保持一致
        interceptorRegistration.addPathPatterns("/**")
                .excludePathPatterns("/**/*.css", "/**/*.js",
                        "/**/*.jpg", "/**/*.png",
                        "/**/*.jpeg", "/**/*.html");

//        InterceptorRegistration interceptorRegistration1 = registry.addInterceptor(loginRequiredInterceptor);
//        interceptorRegistration1.addPathPatterns("/**")
//                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg", "/**/*.html");
    }
}
