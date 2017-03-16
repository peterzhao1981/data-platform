package com.mode.entity;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public class ProductPrice {

    private Long id;
    private Long variantId;
    private String currency = "USD";
    private Long listPriceCent;
    private Long salePriceCent;
    private Integer percentOff;
    private Long saleStart;
    private Long saleEnd;
    private Boolean enabled = true;
    private Long createdAt;
    private Long updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getListPriceCent() {
        return listPriceCent;
    }

    public void setListPriceCent(Long listPriceCent) {
        this.listPriceCent = listPriceCent;
    }

    public Long getSalePriceCent() {
        return salePriceCent;
    }

    public void setSalePriceCent(Long salePriceCent) {
        this.salePriceCent = salePriceCent;
    }

    public Integer getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(Integer percentOff) {
        this.percentOff = percentOff;
    }

    public Long getSaleStart() {
        return saleStart;
    }

    public void setSaleStart(Long saleStart) {
        this.saleStart = saleStart;
    }

    public Long getSaleEnd() {
        return saleEnd;
    }

    public void setSaleEnd(Long saleEnd) {
        this.saleEnd = saleEnd;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
}
