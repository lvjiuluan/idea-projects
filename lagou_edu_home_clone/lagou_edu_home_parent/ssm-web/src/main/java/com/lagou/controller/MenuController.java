package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    // 查询所有菜单信息
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {
        List<Menu> allMenu = menuService.findAllMenu();
        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有菜单成功", allMenu);
        return responseResult;
    }

    // 回显菜单信息
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);
        Map<String, Object> map = new HashMap<>();
        Menu menuInfo = null;
        String message = null;
        if (id == -1) {
            // 添加操作
            message = "添加回显成功";
        } else {
            // 更新操作
            // 1 根据菜单id查询对应信息
            menuInfo = menuService.findMenuById(id);
            message = "修改回显成功";
        }
        map.put("menuInfo", menuInfo);
        map.put("parentMenuList", menuList);
        ResponseResult responseResult = new ResponseResult(true, 200, message, map);
        return responseResult;
    }
}
