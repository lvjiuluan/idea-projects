package com.nowcoder.community.dao;

import com.nowcoder.community.pojo.Post;

public interface PostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Post row);

    int insertSelective(Post row);

    Post selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Post row);

    int updateByPrimaryKeyWithBLOBs(Post row);

    int updateByPrimaryKey(Post row);
}