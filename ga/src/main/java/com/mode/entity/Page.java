package com.mode.entity;

/**
 * Created by zhaoweiwei on 17/1/23.
 */
public class Page {

    private String url;
    private String title;
    private Page parent;
    private Integer depth;

    public Page(String url, String title, Page parent, Integer depth) {
        this.url = url;
        this.title = title;
        this.parent = parent;
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Page getParent() {
        return parent;
    }

    public void setParent(Page parent) {
        this.parent = parent;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
