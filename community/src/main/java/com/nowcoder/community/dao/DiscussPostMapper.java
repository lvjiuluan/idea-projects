package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiscussPostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiscussPost row);

    int insertSelective(DiscussPost row);

    DiscussPost selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiscussPost row);

    int updateByPrimaryKeyWithBLOBs(DiscussPost row);

    int updateByPrimaryKey(DiscussPost row);

    // 分页查询帖子
    // 如果userId == 0，则查询所有帖子
    // 如果userId != 0，则查询该用户所有帖子
    // 这里不是必须要@Param注解，因为可以用param1, param2,param3来指代三个参数的位置
    // 所以不是必须有，但最好有
    List<DiscussPost> selectDiscussPosts(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    // 这里必须要@Param注解
    // 1. userId会出现在if test里面
    // 2. 该方法只有一个参数
    // 3. 只有一个参数时不会用param1来指代，而是调用参数的getXXX方法
    // 所以必须有@Param
    Integer selectDiscussPostRows(@Param("userId") Integer userId);

    // 查询所有的帖子
    List<DiscussPost> selectAll();
}