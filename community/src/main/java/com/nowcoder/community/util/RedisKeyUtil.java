package com.nowcoder.community.util;

public class RedisKeyUtil {
    public static final String SPLIT = ":";
    public static final String PREFIX_ENTITY_LIKE = "like:entity";

    // 某个实体的赞的key
    public static String getEntityLikeKey(Integer entityType, Integer entityId) {
        // like:entity:entityType:entityId -> set{userId}
        // set统计数量
        // set查看谁点了赞
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
