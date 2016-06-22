package com.mode.entity;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
public class StatsDaily {

    // Unique id
    private Integer id;
    // Date
    private Integer date;
    // Daily new registration users count
    private Integer newReg;
    // Daily new registration users from facebook count
    private Integer newRegFb;
    // Daily new registration users from youtube count
    private Integer newRegYt;
    // Total new registration users count
    private Integer totalReg;
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

    public Integer getNewReg() {
        return newReg;
    }

    public void setNewReg(Integer newReg) {
        this.newReg = newReg;
    }

    public Integer getNewRegFb() {
        return newRegFb;
    }

    public void setNewRegFb(Integer newRegFb) {
        this.newRegFb = newRegFb;
    }

    public Integer getNewRegYt() {
        return newRegYt;
    }

    public void setNewRegYt(Integer newRegYt) {
        this.newRegYt = newRegYt;
    }

    public Integer getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(Integer totalReg) {
        this.totalReg = totalReg;
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
