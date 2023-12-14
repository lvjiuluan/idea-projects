package com.lagou.service;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuService {
    // 查询所有菜单
    public List<Menu> findSubMenuListByPid(int pid);

    // 查询所有菜单 不要层级关系
    public List<Menu> findAllMenu();

    // 根据菜单id查询菜单信息
    public Menu findMenuById(Integer mid);
}
