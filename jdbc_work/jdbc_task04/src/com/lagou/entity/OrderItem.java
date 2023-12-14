package com.lagou.entity;

public class OrderItem {
    private String itemid; // 订单项 id
    private String oid;
    private String pid;
    private  Orders orders;
    private Product product;

    public OrderItem() {
    }

    public OrderItem(String itemid, String oid, String pid, Orders orders, Product product) {
        this.itemid = itemid;
        this.oid = oid;
        this.pid = pid;
        this.orders = orders;
        this.product = product;
    }

    /**
     * 获取
     * @return itemid
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * 设置
     * @param itemid
     */
    public void setItemid(String itemid) {
        this.itemid = itemid;
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
     * @return pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置
     * @param pid
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 获取
     * @return orders
     */
    public Orders getOrders() {
        return orders;
    }

    /**
     * 设置
     * @param orders
     */
    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    /**
     * 获取
     * @return product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * 设置
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    public String toString() {
        return "OrderItem{itemid = " + itemid + ", oid = " + oid + ", pid = " + pid + "}";
    }
}
