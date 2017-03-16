package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mode.entity.Logistic17Track;

/**
 * Created by zhaoweiwei on 17/1/26.
 */
public interface Logistic17TrackDao {

    @Update({"<script>",
            "UPDATE logistics_17track_refactor ",
            "<set>",
            "<if test='trackNum != null'>track_num = #{trackNum},</if>",
            "<if test='startDate != null'>start_date = #{startDate},</if>",
            "<if test='state != null'>state = #{state},</if>",
            "<if test='zip != null'>zip = #{zip},</if>",
            "<if test='city != null'>city = #{city},</if>",
            "<if test='userName != null'>user_name = #{userName},</if>",
            "<if test='count != null'>count = #{count},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "</set>",
            "WHERE track_num = #{trackNum}",
            "</script>"})
    public Integer updateLogistic17Track(Logistic17Track logistic17Track);


    @Select("SELECT * FROM logistics_17track_refactor WHERE (status is null or status != 'done') " +
            "and count < 3 order by id limit #{limit}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trackNum", column = "track_num"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "state", column = "state"),
            @Result(property = "zip", column = "zip"),
            @Result(property = "city", column = "city"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "count", column = "count"),
            @Result(property = "status", column = "status")})
    public List<Logistic17Track> getLogistic17Track(Integer limit);

    @Update("UPDATE logistics_17track_refactor set count = count + 1 where track_num = #{trackNum}")
    public Integer updateLogistic17TrackCount(String trackNum);

}
