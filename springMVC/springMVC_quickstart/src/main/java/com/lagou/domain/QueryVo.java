package com.lagou.domain;

import java.util.List;
import java.util.Map;

public class QueryVo {
    private String keyword;
    private User user;
    private List<User> userList;
    private Map<String, User> userMap;

    public QueryVo() {
    }

    public QueryVo(String keyword, User user, List<User> userList, Map<String, User> userMap) {
        this.keyword = keyword;
        this.user = user;
        this.userList = userList;
        this.userMap = userMap;
    }

    /**
     * 获取
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 设置
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 获取
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取
     * @return userList
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * 设置
     * @param userList
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * 获取
     * @return userMap
     */
    public Map<String, User> getUserMap() {
        return userMap;
    }

    /**
     * 设置
     * @param userMap
     */
    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public String toString() {
        return "QueryVo{keyword = " + keyword + ", user = " + user + ", userList = " + userList + ", userMap = " + userMap + "}";
    }
}
