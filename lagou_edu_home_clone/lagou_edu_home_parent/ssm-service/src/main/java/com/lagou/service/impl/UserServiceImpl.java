package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    /*
        用户分页&多条件组合查询
     */
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {

        PageHelper.startPage(userVo.getCurrentPage(), userVo.getPageSize());
        List<User> allUserByPage = userMapper.findAllUserByPage(userVo);

        PageInfo<User> pageInfo = new PageInfo<>(allUserByPage);
        return pageInfo;
    }

    @Override
    public User login(User user) throws Exception {
        User user1 = userMapper.login(user);
        if (user1 == null) {
            return null;
        } else {
            System.out.println(user1.getPhone());
            // 校验密码是否正确
            boolean lagou = Md5.verify(user.getPassword(), "lagou", user1.getPassword());
            if (lagou == true) {
                return user1;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        return userMapper.findUserRelationRoleById(id);
    }

    @Override
    public void allocateUserRole(UserVo userVo) {
        Integer userId = userVo.getUserId();
        // 清空中间表
        userMapper.deleteUserRole(userId);
        for (Integer roleId : userVo.getRoleIdList()) {
            // 建立关联关系
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userId);
            user_role_relation.setRoleId(roleId);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            userMapper.userContexRole(user_role_relation);
        }

    }

    @Override
    public ResponseResult getUserPermissions(Integer userId) {
        // 1 根据当前用户id查询其拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);
        List<Integer> roleIdList = new ArrayList<>();
        for (Role role : roleList) {
            roleIdList.add(role.getId());
        }
        // 2 根据角色ids查询角色所拥有的父级菜单
        List<Menu> parentMenuList = userMapper.findParentMenuByRoleId(roleIdList);
        // 3 根据pid查询子菜单信息
        for (Menu parentMenu : parentMenuList) {
            List<Menu> subMenuList = userMapper.findSubMenuByPid(parentMenu.getId());
            parentMenu.setSubMenuList(subMenuList);
        }
        // 4 根据角色id获取用户拥有的资源权限信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIdList);
        // 5 封装数据并返回
        Map<String, Object> map = new HashMap<>();
        map.put("menuList", parentMenuList);
        map.put("resourceList", resourceList);
        return new ResponseResult(true, 200, "获取用户权限信息成功", map);
    }
}
