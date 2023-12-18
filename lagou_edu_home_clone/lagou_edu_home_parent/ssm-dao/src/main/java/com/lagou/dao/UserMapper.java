package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface UserMapper {

    /*
        用户分页&多条件组合查询
     */
    public List<User> findAllUserByPage(UserVo userVo);

    // 根据用户名查询用户信息
    public User login(User user);

    // 根据用户id清空用户-角色中间表
    public void deleteUserRole(Integer id);

    // 向用户-角色表插入一条数据
    public void userContexRole(User_Role_relation user_role_relation);

    /*
     * 动态菜单
     * */
    // 1. 根据用户id查询管理的角色信息
    public List<Role> findUserRelationRoleById(Integer id);

    // 2. 根据角色id查询角色所拥有的父级菜单
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);

    // 3. 根据pid查询子菜单信息
    public List<Menu> findSubMenuByPid(Integer pid);

    // 4. 根据角色id获取用户拥有的资源权限信息
    public List<Resource> findResourceByRoleId(List<Integer> ids);
}
