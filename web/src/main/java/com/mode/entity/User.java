package com.mode.entity;

/**
 * Created by Ben Hu on 2016/7/20.
 */
public class User {

    private Long id;

    private String password;

    private Integer resetPasswordKey;

    private Long lastPasswordResetTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getResetPasswordKey() {
        return resetPasswordKey;
    }

    public void setResetPasswordKey(Integer resetPasswordKey) {
        this.resetPasswordKey = resetPasswordKey;
    }

    public Long getLastPasswordResetTime() {
        return lastPasswordResetTime;
    }

    public void setLastPasswordResetTime(Long lastPasswordResetTime) {
        this.lastPasswordResetTime = lastPasswordResetTime;
    }
}
