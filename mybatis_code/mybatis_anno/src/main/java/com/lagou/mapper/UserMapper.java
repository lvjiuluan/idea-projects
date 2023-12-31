package com.lagou.mapper;

import com.lagou.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
@CacheNamespace // 配置了二级缓存
public interface UserMapper {
    // 查询所有用户
    @Select("select * from user")
    public List<User> findAll();

    // 添加用户
    @Insert("insert into user (username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})")
    public void save(User user);

    // 更新用户
    @Update("update user set username=#{username},birthday=#{birthday} where id=#{id}")
    public void update(User user);

    // 删除用户
    @Delete("delete from user where id=#{id}")
    public void delete(int id);

    // 根据id查询用户
    @Select("select * from user where id = #{id}")
    public User findById(int id);

    // 查询所有用户 并查询关联的订单信息
    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),
            @Result(property = "ordersList", column = "id", javaType = List.class, many = @Many(
                    select = "com.lagou.mapper.OrderMapper.findOrderById",
                    fetchType = FetchType.LAZY
            ))
    })
    public List<User> findAllWithOrder();
    // 查询所有用户 及 关联的角色信息
    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),
            @Result(property = "roleList", column = "id", javaType = List.class, many = @Many(
                    select = "com.lagou.mapper.RoleMapper.findRoleById",
                    fetchType = FetchType.LAZY
            ))
    })
    public List<User> findAllWithRole();
}
