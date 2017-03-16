package com.mode.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.mode.entity.ProductVariant;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public interface ProductVariantDao {


    /**
     * Create productVariant.
     *
     * @param productVariant
     * @return
     */
    @Insert("INSERT INTO md_product_variant (product_id, sku, upc, title, description, " +
            "quantity, weight, weight_unit, created_at, updated_at) " +
            "VALUES (#{productId}, #{sku}, #{upc}, #{title}, #{description}," +
            "#{quantity}, #{weight}, #{weightUnit}, #{createdAt}, #{updatedAt})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Long.class)
    public Integer createProductVariant(ProductVariant productVariant);

}
