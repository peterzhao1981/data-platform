package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.transaction.annotation.Transactional;

import com.mode.entity.Media;

/**
 * Created by zhaoweiwei on 16/7/21.
 */
public interface MediaDao {

    /**
     * Create media.
     *
     * @param media
     * @return
     */
    @Insert("INSERT INTO md_media (type, file_name, url, width, height, " +
            "created_at, updated_at) " +
            "VALUES (#{type}, #{fileName}, #{url}, #{width}, #{height}, " +
            "#{createdAt}, #{updatedAt})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Long.class)
    public Integer createMedia(Media media);


    /**
     * Get media by id.
     *
     * @param id
     * @return
     */
    @Select("select * from md_media where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "url", column = "url"),
            @Result(property = "width", column = "width"),
            @Result(property = "height", column = "height"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")})
    public Media getMedia(Long id);

}
