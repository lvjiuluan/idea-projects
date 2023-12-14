package com.lagou.domain;

import java.util.List;

public class Role {
    private int id;
    private String rolename;
    private String roleDesc;
    private List<User> userList;


    public Role() {
    }

    public Role(int id, String rolename, String roleDesc, List<User> userList) {
        this.id = id;
        this.rolename = rolename;
        this.roleDesc = roleDesc;
        this.userList = userList;
    }

    /**
     * 获取
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * 设置
     *
     * @param rolename
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * 获取
     *
     * @return roleDesc
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * 设置
     *
     * @param roleDesc
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * 获取
     *
     * @return userList
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * 设置
     *
     * @param userList
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String toString() {
        return "Role{id = " + id + ", rolename = " + rolename + ", roleDesc = " + roleDesc + ", userList = " + userList + "}";
    }
}
