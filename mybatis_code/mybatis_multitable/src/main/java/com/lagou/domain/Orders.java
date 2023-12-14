package com.lagou.domain;

public class Orders {
    private int id;
    private String ordertime;
    private double total;
    private int uid;

    // 多对一，订单属于哪个用户
    private User user;


    public Orders() {
    }

    public Orders(int id, String ordertime, double total, int uid, User user) {
        this.id = id;
        this.ordertime = ordertime;
        this.total = total;
        this.uid = uid;
        this.user = user;
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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
     * @return uid
     */
    public int getUid() {
        return uid;
    }

    /**
     * 设置
     * @param uid
     */
    public void setUid(int uid) {
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
        return "Orders{id = " + id + ", ordertime = " + ordertime + ", total = " + total + ", uid = " + uid + ", user = " + user + "}";
    }
}
