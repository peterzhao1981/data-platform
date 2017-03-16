package com.mode.entity;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
public class GaDailyStats {

    private Long id;
    private Integer date;
    private Long runTime;
    private String ip;
    private String clientId;
    private String userAgent;
    private String geoId;
    private String countryCode;
    private Boolean bounceOut = false;
    private Boolean revisit = false;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Boolean getBounceOut() {
        return bounceOut;
    }

    public void setBounceOut(Boolean bounceOut) {
        this.bounceOut = bounceOut;
    }

    public Boolean getRevisit() {
        return revisit;
    }

    public void setRevisit(Boolean revisit) {
        this.revisit = revisit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
