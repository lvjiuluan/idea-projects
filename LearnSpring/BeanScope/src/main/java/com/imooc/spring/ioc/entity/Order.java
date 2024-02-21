package com.imooc.spring.ioc.entity;

public class Order {
    private Float price;
    private Integer quantity;
    private Float total;

    public Order() {
        System.out.println("创建Order对象: " + this);
    }

    public void destroy(){
        System.out.println("IoC容器销毁前执行的方法...");
    }

    public void init() {
        System.out.println("执行init方法");
        total = price * quantity;
    }

    public void pay() {
        System.out.println("订单金额为: " + this.total);
    }

    public Float getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getTotal() {
        return total;
    }

    public void setPrice(Float price) {
        System.out.println("设置price: " + price);
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        System.out.println("设置quantity: " + quantity);
        this.quantity = quantity;
    }

    public void setTotal(Float total) {
        System.out.println("设置total: " + total);
        this.total = total;
    }
}
