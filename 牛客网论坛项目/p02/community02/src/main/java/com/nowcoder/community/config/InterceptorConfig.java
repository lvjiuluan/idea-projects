package com.nowcoder.community.config;

import com.nowcoder.community.interceptor.InterceptorA;
import com.nowcoder.community.interceptor.InterceptorB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private InterceptorA interceptorA;

    @Autowired
    private InterceptorB interceptorB;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorA)
                .addPathPatterns("/demo/hello")
                .excludePathPatterns("/**/*.js","/**/*.html","/**/*.css");
        registry.addInterceptor(interceptorB)
                .addPathPatterns("/demo/hello")
                .excludePathPatterns("/**/*.js","/**/*.html","/**/*.css");
    }
}
