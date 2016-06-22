package com.mode.entity;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
public class Calendar {

    private Integer id;
    private Integer date;
    private Integer weekend;
    private Integer month;
    private String quarter;
    private Integer year;
    private Long startTs;
    private Long endTs;

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

    public Integer getWeekend() {
        return weekend;
    }

    public void setWeekend(Integer weekend) {
        this.weekend = weekend;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getStartTs() {
        return startTs;
    }

    public void setStartTs(Long startTs) {
        this.startTs = startTs;
    }

    public Long getEndTs() {
        return endTs;
    }

    public void setEndTs(Long endTs) {
        this.endTs = endTs;
    }
}
