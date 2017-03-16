package com.mode.entity;

import java.util.List;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public class Product {

    private Long id;
    private Long brandId;
    private String title;
    private String description;
    private Long coverMediaId;
    private Boolean proprietary = false;
    private Boolean archived = false;
    private String externalId;
    private Long createdAt;
    private Long updatedAt;
    private List<Media> medias;
    private List<ProductVariant> productVariants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCoverMediaId() {
        return coverMediaId;
    }

    public void setCoverMediaId(Long coverMediaId) {
        this.coverMediaId = coverMediaId;
    }

    public Boolean getProprietary() {
        return proprietary;
    }

    public void setProprietary(Boolean proprietary) {
        this.proprietary = proprietary;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
