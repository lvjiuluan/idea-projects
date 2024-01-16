package com.nowcoder.community.service;

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.data.domain.Page;

public interface IElasticsearchService {
    void saveDiscussPost(DiscussPost discussPost);

    void deleteDiscussPost(Integer id);

    Page<DiscussPost> searchDiscussPost(String keyword, Integer cuttent, Integer limit);
}
