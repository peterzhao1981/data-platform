package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mode.common.ClubFactoryData;

/**
 * Created by zhaoweiwei on 17/1/10.
 */
public interface ClubFactoryDataDao {

    @Insert("INSERT INTO club_factory_data (item_id, item_detail, image_url, urls_1688, " +
            "created_at, updated_at) " +
            "values (#{itemId}, #{itemDetail}, #{imageUrl}, #{urls1688}, #{createdAt}, " +
            "#{updatedAt})")
    public Integer createClubFactoryData(ClubFactoryData clubFactoryData);

    @Select("SELECT * FROM club_factory_data WHERE item_id = #{itemId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemDetail", column = "item_detail"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "urls1688", column = "urls_1688"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")})
    public ClubFactoryData getClubFactoryData(Long itemId);

    @Select({"<script>",
            "SELECT * FROM club_factory_data ",
            "ORDER BY updated_at DESC ",
            "<if test='limit != null'> LIMIT #{limit}</if>",
            "<if test='offset != null'> OFFSET #{offset}</if>",
            "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemDetail", column = "item_detail"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "urls1688", column = "urls_1688"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")})
    public List<ClubFactoryData> listClubFactoryData(@Param("offset") Integer offset,
                                                     @Param("limit") Integer limit);

    @Update({"<script>",
            "UPDATE club_factory_data ",
            "<set>",
            "<if test='itemId != null'>item_id = #{itemId},</if>",
            "<if test='itemDetail != null'>item_detail = #{itemDetail},</if>",
            "<if test='imageUrl != null'>image_url = #{imageUrl},</if>",
            "<if test='urls1688 != null'>urls_1688 = #{urls1688},</if>",
            "<if test='updatedAt != null'>updated_at = #{updatedAt},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"})
    public Integer updateClubFactoryData(ClubFactoryData clubFactoryData);

    @Select("SELECT count(*) FROM club_factory_data")
    public Integer countClubFactoryData();
}
