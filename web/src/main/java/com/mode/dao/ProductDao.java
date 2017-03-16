package com.mode.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import com.mode.entity.Product;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public interface ProductDao {

    /**
     * Create product.
     *
     * @param product
     * @return
     */
    @Insert("INSERT INTO md_product (brand_id, title, description, cover_media_id, proprietary, " +
            "archived, external_id, created_at, updated_at) " +
            "VALUES (#{brandId}, #{title}, #{description}, #{coverMediaId}, #{proprietary}, " +
            "#{archived}, #{externalId}, #{createdAt}, #{updatedAt})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Long.class)
    public Integer createProduct(Product product);


    /**
     * Get product by external id.
     *
     * @param externalId
     * @return
     */
    @Select("select * from md_product where external_id = #{externalId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "brandId", column = "brand_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "coverMediaId", column = "cover_media_id"),
            @Result(property = "proprietary", column = "proprietary"),
            @Result(property = "archived", column = "archived"),
            @Result(property = "externalId", column = "external_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")})
    public Product getProductByExternalId(String externalId);

    /**
     * Update product cover media.
     *
     * @param productId
     * @param mediaId
     * @return
     */
    @Update("update md_product set cover_media_id = #{mediaId} where id = #{productId}")
    public Integer updateProductCoverMedia(@Param("productId") Long productId,
                                           @Param("mediaId") Long mediaId);
}
