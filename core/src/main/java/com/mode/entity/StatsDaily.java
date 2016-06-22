package com.mode.entity;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
public class StatsDaily {

    // Unique id
    private Integer id;
    // Date
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
    private Integer actives;
    // Daily orders count
    private Integer orders;
    // Total orders count
    private Integer totalOrders;
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

    public Integer getActives() {
        return actives;
    }

    public void setActives(Integer actives) {
        this.actives = actives;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
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
