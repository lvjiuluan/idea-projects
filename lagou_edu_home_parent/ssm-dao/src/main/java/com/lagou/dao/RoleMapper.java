package com.lagou.dao;

import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

public interface RoleMapper {
    // 查询所有角色  条件查询
    public List<Role> findAllRole(Role role);

    // 根据角色id查询该角色关联的菜单信息ID
    public List<Integer> findMenuByRoleId(Integer roleId);

    // 根据角色id情况角色-菜单中间表的记录
    public void deleteRoleContextMenu(Integer roleId);

    // 为角色分配一个菜单信息
    public void roleContextMenu(Role_menu_relation role_menu_relation);

    // 根据角色id删除角色
    public void deleteRole(Integer roleId);
}
