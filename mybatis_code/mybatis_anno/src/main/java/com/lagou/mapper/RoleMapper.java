package com.lagou.mapper;

import com.lagou.domain.Role;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    // 根据用户id查询角色信息
    @Select("select * from sys_role r inner join sys_user_role ur on ur.roleid = r.id where ur.userid = #{uid}")
    public List<Role> findRoleById(int uid);
}
