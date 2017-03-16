package com.mode.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.mode.entity.ProductPrice;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public interface ProductPriceDao {

    /**
     * Create productPrice.
     *
     * @param productPrice
     * @return
     */
    @Insert("INSERT INTO md_product_price (variant_id, currency, list_price_cent, " +
            "sale_price_cent, percent_off, sale_start, sale_end, enabled," +
            "created_at, updated_at) " +
            "VALUES (#{variantId}, #{currency}, #{listPriceCent}, #{salePriceCent}, " +
            "#{percentOff}, #{saleStart}, #{saleEnd}, #{enabled}, #{createdAt}, #{updatedAt})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Long.class)
    public Integer createProductPrice(ProductPrice productPrice);

}
