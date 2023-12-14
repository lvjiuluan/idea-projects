package com.lagou.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    // 当前用户所具有的订单列表
    private List<Orders> ordersList;
    // 当前用户所具有的角色
    private List<Role> roleList;

    public User() {
    }

    public User(Integer id, String username, Date birthday, String sex, String address, List<Orders> ordersList, List<Role> roleList) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.address = address;
        this.ordersList = ordersList;
        this.roleList = roleList;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取
     * @return ordersList
     */
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    /**
     * 设置
     * @param ordersList
     */
    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    /**
     * 获取
     * @return roleList
     */
    public List<Role> getRoleList() {
        return roleList;
    }

    /**
     * 设置
     * @param roleList
     */
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String toString() {
        return "User{id = " + id + ", username = " + username + ", birthday = " + birthday + ", sex = " + sex + ", address = " + address + ", ordersList = " + ordersList + ", roleList = " + roleList + "}";
    }
}
