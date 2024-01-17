package com.nowcoder.community.interceptor;

import com.google.gson.Gson;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IMessageService;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/*
 * 1 先有拦截器
 * 2 对拦截器进行注册，得到“注册过的拦截器”
 * 3 通过“注册过的拦截器”设置拦截的url
 * */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IMessageService messageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = RequestUtil.getCookie(request, "ticket");
        if (ticket != null) {
            // 根据ticket查出loginTicket
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            if (loginTicket != null && loginTicket.getStatus().equals(0)
                    && loginTicket.getExpired().after(new Date())) {
                // 没有失效，且没有过期
                // 根据凭证中的userId查询出user的信息
                User user = userService.findUserById(loginTicket.getUserId());
                /*
                 * findUserById这个方法要去数据库查，不方便，因为这里会被调用很多次
                 * findUserById改造为从redis查用户信息
                 *
                 * */

                // 将user信息存入请求中，让本次请求中持有用户
                // 用一个容器存要考虑线程安全
                hostHolder.setUser(user);
                // 构建用户认证的结果，并存入SecurityContext
                // 以便于Security进行授权
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        userService.getAuthorities(user.getId())
                );
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
                // 然后在postHandle里面把user存入model中
            }
        }
        // 抛出登录异常，去异常捕获那处理
        // 跳转应该是前端的逻辑
        // 这里不放回false，如果user查不到，前端是不会显示的
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            Map<String, Object> model = modelAndView.getModel();
            model.put("loginUser", user);
            Integer letterUnderReadCount = messageService.findLetterUnderReadCount(hostHolder.getUser().getId(), "");
            Integer unreadNoticeCount = messageService.findUnreadNoticeCount(hostHolder.getUser().getId(), "");

            model.put("unreadMessage", letterUnderReadCount + unreadNoticeCount);
            log.info("当前的视图名：{}", modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
        SecurityContextHolder.clearContext();
    }
}
