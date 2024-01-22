package com.nowcoder.community.service;

import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IUserService {
    // 根据用户id查询用户
    // 尽量保持一致，学习别人怎么命名
    // 重构
    User findUserById(Integer id);

    // 注册的业务
    Map<String, Object> register(User user);

    // 激活新注册的账号
    Integer activation(Integer id, String activationCode);

    // 登录的业务
    Map<String, Object> login(String username, String password, Long expiredSession);

    // 退出登录业务
    void logout(String ticket);

    // 上传用户头像 返回图像名称
    void upload(String ticket, MultipartFile multipartFile);

    // 修改密码
    Map<String, Object> changePassword(String ticket, String original, String now);

    // 根据用户名查询用户
    User findUserByUsername(String username);

    List<User> findUsersByIdList(List<Integer> entityIdList);

    // 查询登录凭证的用户
    LoginTicket findLoginTicket(String ticket);

    // 根据用户获取权限
    Collection<? extends GrantedAuthority> getAuthorities(Integer userId);

    // 修改userr
    Integer updateUser(User user);
}
