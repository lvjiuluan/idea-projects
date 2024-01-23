package com.nowcoder.community.config;

import com.nowcoder.community.constant.UserRoleConst;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * TODO: 1 修改帖子状态要触发发帖事件，然后修改elasticsearch里面的帖子
 *       2 删除要触发删除帖子事件,从es将帖子删除
 *       3 直接在 http.authorizeRequests()后面写
 *
 * */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 授权
        http.authorizeRequests().antMatchers(
                        "/setting",
                        "/uploadFile",
                        "/post/add",
                        "comment/add",
                        "/letter/**",
                        "/notice/**",
                        "/like",
                        "/follow",
                        "unfollow"
                ).hasAnyAuthority(
                        UserRoleConst.USER,
                        UserRoleConst.ADMIN,
                        UserRoleConst.MODERATOR)
                .antMatchers(
                        "/top",
                        "/highlight"
                ).hasAnyAuthority(UserRoleConst.MODERATOR)
                .antMatchers(
                        "/delete",
                        "/data/**",
                        "/actuator/**"

                ).hasAnyAuthority(UserRoleConst.ADMIN)
                .anyRequest().permitAll().and().csrf().disable();

        // 权限不够时的处理
        http.exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    // 权限不足，已登录怎么处理
                    @Override
                    public void handle(HttpServletRequest request,
                                       HttpServletResponse response,
                                       AccessDeniedException e)
                            throws IOException, ServletException {
                        String xRequestedWith = request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {
                            response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403, "你没有访问此功能的权限"));
                        } else {
                            response.sendRedirect(request.getContextPath() + "/denied");
                        }
                    }
                })
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 权限不足，未没登录时的处理
                    @Override
                    public void commence(HttpServletRequest request,
                                         HttpServletResponse response,
                                         AuthenticationException e)
                            throws IOException, ServletException {
                        String xRequestedWith = request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {
                            response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403, "你还没有登录"));
                        } else {
                            response.sendRedirect(request.getContextPath() + "/login");
                        }
                    }
                });
        // Security底层默认会拦截logout请求进行退出的处理
        // 我们覆盖它的默认的逻辑
        // 执行我们自己退出的代码
        http.logout().logoutUrl("/security/logout");

    }
}
