package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mode.entity.GaDailyProcess;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
public interface GaDailyProcessDao {

    @Insert("INSERT INTO ga_daily_process (run_time, ip, client_id, user_agent, geo_id, " +
            "url, title, end, country_code, status) " +
            "values (#{runTime}, #{ip}, #{clientId}, #{userAgent}, #{geoId}, #{url}, #{title}, " +
            "#{end}, #{countryCode}, #{status})")
    public Integer createGaDailyProcess(GaDailyProcess gaDailyProcess);

    @Select("SELECT * FROM ga_daily_process WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "runTime", column = "run_time"),
            @Result(property = "ip", column = "ip"),
            @Result(property = "clientId", column = "client_id"),
            @Result(property = "userAgent", column = "user_agent"),
            @Result(property = "geoId", column = "geo_id"),
            @Result(property = "url", column = "url"),
            @Result(property = "title", column = "title"),
            @Result(property = "end", column = "end"),
            @Result(property = "countryCode", column = "country_code"),
            @Result(property = "status", column = "status")})
    public GaDailyProcess getGaDailyProcess(Integer id);

    @Select("SELECT * FROM ga_daily_process WHERE run_time <= #{current} and status is null " +
            "order by id for update")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "runTime", column = "run_time"),
            @Result(property = "ip", column = "ip"),
            @Result(property = "clientId", column = "client_id"),
            @Result(property = "userAgent", column = "user_agent"),
            @Result(property = "geoId", column = "geo_id"),
            @Result(property = "url", column = "url"),
            @Result(property = "title", column = "title"),
            @Result(property = "end", column = "end"),
            @Result(property = "countryCode", column = "country_code")})
    public List<GaDailyProcess> getDailyProcessesForUpdate(Long current);

    @Update({"<script>",
            "UPDATE ga_daily_process ",
            "<set>",
            "<if test='runTime != null'>run_time = #{runTime},</if>",
            "<if test='ip != null'>ip = #{ip},</if>",
            "<if test='clientId != null'>client_id = #{clientId},</if>",
            "<if test='userAgent != null'>user_agent = #{userAgent},</if>",
            "<if test='geoId != null'>geo_id = #{geoId},</if>",
            "<if test='url != null'>url = #{url},</if>",
            "<if test='title != null'>title = #{title},</if>",
            "<if test='end != null'>end = #{end},</if>",
            "<if test='countryCode != null'>country_code = #{countryCode},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"})
    public Integer updateGaDailyProcess(GaDailyProcess gaDailyProcess);

    @Update({"<script>",
            "UPDATE ga_daily_process ",
            "<set>",
            "<if test='userAgent != null'>user_agent = #{userAgent},</if>",
            "<if test='geoId != null'>geo_id = #{geoId},</if>",
            "</set>",
            "WHERE ip = #{ip}",
            "</script>"})
    public Integer updateUserAgentAndGeoId(@Param("ip") String ip,
                                           @Param("userAgent") String userAgent,
                                           @Param("geoId") String geoId);
}
