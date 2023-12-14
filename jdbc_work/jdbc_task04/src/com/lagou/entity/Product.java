package com.lagou.entity;

public class Product {
    private String pid;
    private String pname;
    private double price;
    private String pdesc;
    private int pflag;  //商品是否上架，1 上架 0 下架
    // 定义外键
    private String cid; // 类别
    private Category category; // 保存商品分类的详细信息

    public Product() {
    }

    public Product(String pid, String pname, double price, String pdesc, int pflag, String cid, Category category) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.pdesc = pdesc;
        this.pflag = pflag;
        this.cid = cid;
        this.category = category;
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
     * @return pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * 设置
     * @param pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * 获取
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * 设置
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 获取
     * @return pdesc
     */
    public String getPdesc() {
        return pdesc;
    }

    /**
     * 设置
     * @param pdesc
     */
    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    /**
     * 获取
     * @return pflag
     */
    public int getPflag() {
        return pflag;
    }

    /**
     * 设置
     * @param pflag
     */
    public void setPflag(int pflag) {
        this.pflag = pflag;
    }

    /**
     * 获取
     * @return cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * 设置
     * @param cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * 获取
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * 设置
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    public String toString() {
        return "Product{pid = " + pid + ", pname = " + pname + ", price = " + price + ", pdesc = " + pdesc + ", pflag = " + pflag + ", cid = " + cid + "}";
    }
}
