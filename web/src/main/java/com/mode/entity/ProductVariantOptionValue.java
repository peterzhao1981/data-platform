package com.mode.entity;

/**
 * Created by zhaoweiwei on 16/7/22.
 */
public class ProductVariantOptionValue {

    private Long variantId;
    private Long optionValueId;

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Long getOptionValueId() {
        return optionValueId;
    }

    public void setOptionValueId(Long optionValueId) {
        this.optionValueId = optionValueId;
    }
}