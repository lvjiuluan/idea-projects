package com.lagou.mapper;

import com.lagou.domain.Role;

import java.util.List;

public interface RoleMapper {
    // 根据uid查询角色信息
    public List<Role> findByUid(int uid);
}
