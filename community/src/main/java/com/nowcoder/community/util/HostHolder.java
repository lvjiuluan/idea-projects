package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/*
 *
 * 起到一个容器的作用，代替session对象
 * */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        // 以线程为Key，获取当前线程的user
        return users.get();
    }

    public void clear() {
        // 清理当前线程的map
        users.remove();
    }
}
