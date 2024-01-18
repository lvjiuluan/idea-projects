package com.nowcoder.community.interceptor;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IDataService;
import com.nowcoder.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class DataInterceptor implements HandlerInterceptor {
    @Autowired
    private IDataService dataService;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 统计UV
        // 1 获取ip
        String ip = request.getRemoteHost();
        // 2 记录
        dataService.recordUV(ip);

        // 统计DAU
        // 1 获取userId
        User user = hostHolder.getUser();
        if (user != null) {
            dataService.recordDAU(user.getId());
        }

        return true;
    }
}
