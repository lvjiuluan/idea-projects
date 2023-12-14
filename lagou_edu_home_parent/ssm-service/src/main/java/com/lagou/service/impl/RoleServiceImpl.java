package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        return roleMapper.findAllRole(role);
    }

    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        return roleMapper.findMenuByRoleId(roleId);
    }

    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {
        Integer roleId = roleMenuVo.getRoleId();
        // 清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleId);
        // 一个一个为角色分配菜单
        for (Integer integer : roleMenuVo.getMenuIdList()) {
            // 封装对象
            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setRoleId(roleId);
            role_menu_relation.setMenuId(integer);
            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");

            roleMapper.roleContextMenu(role_menu_relation);
        }

    }

    @Override
    public void deleteRole(Integer roleId) {
        roleMapper.deleteRoleContextMenu(roleId);
        roleMapper.deleteRole(roleId);
    }


}
