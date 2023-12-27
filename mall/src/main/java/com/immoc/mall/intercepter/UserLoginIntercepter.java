package com.immoc.mall.intercepter;


import com.immoc.mall.exception.UserLoginException;
import com.immoc.mall.pojo.User;
import com.immoc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.immoc.mall.consts.MallConst.CURRENT_USER;
import static com.immoc.mall.enums.ResponseEnum.NEED_LOGIN;

@Slf4j
public class UserLoginIntercepter implements HandlerInterceptor {
    /*
     *
     * true: 表示继续
     * false：表示中断
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CURRENT_USER);
        if (user == null) {
            log.info("user == null");
            throw new UserLoginException();
        }
        return true;
    }
}
