package com.mode.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void main(String[] args) {

        List<Post> list = new ArrayList<Post>();

        Post post = new Post();
        post.setId(1);
        post.setTitle("t1");
        post.setComments(1);
        post.setFavors(1);
        post.setShares(1);
        post.setViews(1);
        list.add(post);

        post = new Post();
        post.setId(2);
        post.setTitle("t2");
        post.setComments(2);
        post.setFavors(2);
        post.setShares(2);
        post.setViews(2);
        list.add(post);

        post = new Post();
        post.setId(3);
        post.setTitle("t2");
        post.setComments(3);
        post.setFavors(3);
        post.setShares(3);
        post.setViews(3);
        list.add(post);

        Stream stream = list.stream().filter(p -> "t2".equals(p.getTitle()));
        System.out.println(stream.count());
        List<Post> newList = list.stream().filter(p -> "t2".equals(p.getTitle())).collect(Collectors.toList());

        System.out.println(newList.size());
    }
}
