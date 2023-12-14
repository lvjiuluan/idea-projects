package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;

import java.util.List;

public interface UserService {

    /*
        用户分页&多条件查询
     */
    public PageInfo findAllUserByPage(UserVo userVo);

    // 根据用户名查询用户信息
    public User login(User user) throws Exception;

    // 根据用户id查询管理的角色信息 分配角色(回显)
    public List<Role> findUserRelationRoleById(Integer id);

    // 给用户分配角色
    public void allocateUserRole(UserVo userVo);

    // 获取用户权限，进行菜单动态展示的方法
    public ResponseResult getUserPermissions(Integer userId);
}
