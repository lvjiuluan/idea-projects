package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IUserService {
    // 根据用户id查询用户
    // 尽量保持一致，学习别人怎么命名
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
}
