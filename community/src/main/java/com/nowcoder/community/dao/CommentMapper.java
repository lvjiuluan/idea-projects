package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment row);

    int insertSelective(Comment row);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment row);

    int updateByPrimaryKeyWithBLOBs(Comment row);

    int updateByPrimaryKey(Comment row);

    // 有多个参数，必须写@Param，不然就只能用param1,param2代替
    // 根据  实体类型和实体id 查询出所有的子评论,
    List<Comment> selectCommentListByEntity(@Param("entityType") Integer entityType,
                                            @Param("entityId") Integer entityId,
                                            @Param("offset") Integer offset,
                                            @Param("limit") Integer limit);

    // 根据  实体类型和实体id 查询出所有的子评论的总行数
    Integer selectTotalRowsByEntity(@Param("entityType") Integer entityType, @Param("entityId") Integer entityId);

}