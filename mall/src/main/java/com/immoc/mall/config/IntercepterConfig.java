package com.immoc.mall.config;

import com.immoc.mall.intercepter.UserLoginIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer {
    @Autowired
    private FilterUrl filterUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器
        /*
         *   /**表示默认对所有url拦截
         * */
        registry.addInterceptor(new UserLoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns(filterUrl.getFilterUrl().split(" "));
    }
}
