package com.mode.entity;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
public class StatsDaily {

    // Unique id
    private Integer id;
    // Daily Date
    private Integer date;
    // Daily new users count
    private Integer newUser;
    // Daily new users from facebook count
    private Integer newUserFb;
    // Daily new  users from youtube count
    private Integer newUserYt;
    // Total new users count
    private Integer totalUser;
    // Daily active users count
    private Integer activeUser;
    // Daily orders count
    private Integer order;
    // Total orders count
    private Integer totalOrder;
    // Daily gmv
    private Float gmv;
    // Total gmv
    private Float totalGmv;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getNewUser() {
        return newUser;
    }

    public void setNewUser(Integer newUser) {
        this.newUser = newUser;
    }

    public Integer getNewUserFb() {
        return newUserFb;
    }

    public void setNewUserFb(Integer newUserFb) {
        this.newUserFb = newUserFb;
    }

    public Integer getNewUserYt() {
        return newUserYt;
    }

    public void setNewUserYt(Integer newUserYt) {
        this.newUserYt = newUserYt;
    }

    public Integer getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Integer totalUser) {
        this.totalUser = totalUser;
    }

    public Integer getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Integer activeUser) {
        this.activeUser = activeUser;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Float getGmv() {
        return gmv;
    }

    public void setGmv(Float gmv) {
        this.gmv = gmv;
    }

    public Float getTotalGmv() {
        return totalGmv;
    }

    public void setTotalGmv(Float totalGmv) {
        this.totalGmv = totalGmv;
    }
}
