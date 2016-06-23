package com.mode.entity;

/**
 * Created by zhaoweiwei on 16/6/22.
 */
public class StatsMonthly {

    // Unique id
    private Integer id;
    // Monthly date
    private Integer date;
    // Monthly active users count
    private Integer activeUser;

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

    public Integer getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Integer activeUser) {
        this.activeUser = activeUser;
    }
}
