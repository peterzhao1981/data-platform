package com.mode.entity;

/**
 * Created by kang on 2016/7/1.
 */
public class RelatedProduct {

    private Integer id;
    private Long postId;
    private Long productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
