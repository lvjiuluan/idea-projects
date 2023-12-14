package com.lagou.entity;

public class User {
    private String uid;
    private String username;
    private String password;
    private String telepthon;
    private String birthday;
    private String sex;

    public User() {
    }

    public User(String uid, String username, String password, String telepthon, String birthday, String sex) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.telepthon = telepthon;
        this.birthday = birthday;
        this.sex = sex;
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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return telepthon
     */
    public String getTelepthon() {
        return telepthon;
    }

    /**
     * 设置
     * @param telepthon
     */
    public void setTelepthon(String telepthon) {
        this.telepthon = telepthon;
    }

    /**
     * 获取
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置
     * @param birthday
     */
    public void setBirthday(String birthday) {
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

    public String toString() {
        return "User{uid = " + uid + ", username = " + username + ", password = " + password + ", telepthon = " + telepthon + ", birthday = " + birthday + ", sex = " + sex + "}";
    }
}
