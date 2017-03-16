package com.mode.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.mode.entity.ProductVariantOptionValue;

/**
 * Created by zhaoweiwei on 16/7/22.
 */
public interface ProductVariantOptionValueDao {

    /**
     * Create productVariantOptionValue.
     *
     * @param productVariantOptionValue
     * @return
     */
    @Insert("INSERT INTO md_product_variant_option_value (variant_id, option_value_id) " +
            "VALUES (#{variantId}, #{optionValueId})")
    public Integer createProductVariantOptionValue(ProductVariantOptionValue
                                                               productVariantOptionValue);
}
