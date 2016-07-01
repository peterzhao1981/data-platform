package com.mode.entity;

/**
 * Created by kang on 2016/6/30.
 */
public class Post {

    private Integer id;
    private String title;
    private Integer views;
    private Integer favors;
    private Integer comments;
    private Integer shares;
    private Integer relatedItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getFavors() {
        return favors;
    }

    public void setFavors(Integer favors) {
        this.favors = favors;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(Integer relatedItem) {
        this.relatedItem = relatedItem;
    }
}
