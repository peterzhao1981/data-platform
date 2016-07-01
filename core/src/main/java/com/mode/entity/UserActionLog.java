package com.mode.entity;


public class UserActionLog {

    private Long id;
    private Integer userId;
    private String sourceType;
    private String sourceValue;
    private String action;
    private String objectType;
    private String objectValue;
    private String extraValue;
    private Long ctime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(String objectValue) {
        this.objectValue = objectValue;
    }

    public String getExtraValue() {
        return extraValue;
    }

    public void setExtraValue(String extraValue) {
        this.extraValue = extraValue;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "{"
                + " \"id\":\"" + id + "\""
                + ", \"userId\":\"" + userId + "\""
                + ", \"sourceType\":\"" + sourceType + "\""
                + ", \"sourceValue\":\"" + sourceValue + "\""
                + ", \"action\":\"" + action + "\""
                + ", \"objectType\":\"" + objectType + "\""
                + ", \"objectValue\":\"" + objectValue + "\""
                + ", \"extraValue\":\"" + extraValue + "\""
                + ", \"ctime\":\"" + ctime + "\""
                + "}";
    }
}
