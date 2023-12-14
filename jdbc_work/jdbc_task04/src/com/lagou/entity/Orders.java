package com.lagou.entity;

import java.util.ArrayList;
import java.util.List;

public class Orders {
    private String oid;
    private String ordertime;
    private double total;
    private String name;
    private String telephone;
    private String address;
    private int state; // 订单状态: 1代表已支付，0代码未支付
    // 定义外键uid
    private String uid;
    private User user;
    // 描述多对一的关系，一个订单中包含多个订单项
    private List<OrderItem> list = new ArrayList<>();

    public Orders() {
    }

    public Orders(String oid, String ordertime, double total, String name, String telephone, String address, int state, String uid, User user) {
        this.oid = oid;
        this.ordertime = ordertime;
        this.total = total;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.state = state;
        this.uid = uid;
        this.user = user;
    }

    /**
     * 获取
     * @return oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * 设置
     * @param oid
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * 获取
     * @return ordertime
     */
    public String getOrdertime() {
        return ordertime;
    }

    /**
     * 设置
     * @param ordertime
     */
    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    /**
     * 获取
     * @return total
     */
    public double getTotal() {
        return total;
    }

    /**
     * 设置
     * @param total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * 获取
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
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

    public String toString() {
        return "Orders{oid = " + oid + ", ordertime = " + ordertime + ", total = " + total + ", name = " + name + ", telephone = " + telephone + ", address = " + address + ", state = " + state + ", uid = " + uid + "}";
    }
}
