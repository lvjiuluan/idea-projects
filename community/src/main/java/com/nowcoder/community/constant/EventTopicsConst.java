package com.nowcoder.community.constant;

public class EventTopicsConst {
    public static final String COMMENT = "comment";
    public static final String LIKE = "like";
    public static final String FOLLOW = "follow";
    public static final String PUBLISH = "PUBLISH";
    public static final String DELETE = "delete";

    public static final String getAction(String action) {
        if (COMMENT.equals(action)) return "评论";
        if (LIKE.equals(action)) return "点赞";
        if (FOLLOW.equals(action)) return "关注";
        return null;
    }
}
