package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    // 多条件&分页查询
    @RequestMapping("/findAllResource")
    public ResponseResult findAllResourceByPage(@RequestBody ResourseVo resourseVo) {
        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourseVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "多条件&分页查询资源成功", pageInfo);
        return responseResult;
    }
}
