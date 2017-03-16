package com.mode.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.mode.entity.Product;
import com.mode.entity.ProductOptionValue;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public interface ProductOptionValueDao {

    /**
     * Create productOptionValue.
     *
     * @param productOptionValue
     * @return
     */
    @Insert("INSERT INTO md_product_option_value (product_option_id, value, display_order) " +
            "VALUES (#{productOptionId}, #{value}, #{displayOrder})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Long.class)
    public Integer createProductOptionValue(ProductOptionValue productOptionValue);


    /**
     * Get max display order.
     *
     * @param productOptionId
     * @return
     */
    @Select("select max(display_order) from md_product_option_value " +
            "where product_option_id = #{productOptionId}")
    public Integer getMaxDisplayOrder(Long productOptionId);


    /**
     * Get productOptionValue.
     *
     * @param value
     * @return
     */
    @Select("select * from md_product_option_value " +
            "where value = #{value} and product_option_id = #{productOptionId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "productOptionId", column = "product_option_id"),
            @Result(property = "value", column = "value"),
            @Result(property = "displayOrder", column = "display_order")})
    public ProductOptionValue getProductOptionValue(@Param("value") String value,
                                                    @Param("productOptionId") Long productOptionId);
}
