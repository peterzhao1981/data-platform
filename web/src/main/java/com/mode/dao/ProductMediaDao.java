package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import com.mode.entity.Media;
import com.mode.entity.ProductMedia;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public interface ProductMediaDao {

    /**
     * Create productMedia.
     *
     * @param productMedia
     * @return
     */
    @Insert("INSERT INTO md_product_media (product_id, media_id) " +
            "VALUES (#{productId}, #{mediaId})")
    public Integer createProductMedia(ProductMedia productMedia);


    /**
     * Batch create productMedias.
     *
     * @param productMedias
     * @return
     */
    @Insert({"<script>",
            "insert into md_product_media (product_id, media_id) ",
            "values ",
            "<foreach collection='productMedias' item='productMedia' index='index' ",
            "open='(' separator='),(' close=')'>",
            "#{productMedia.productId}, #{productMedia.mediaId}",
            "</foreach>",
            "</script>"})
    public Integer createProductMediaBatch(@Param("productMedias")
                                               List<ProductMedia> productMedias);

}
