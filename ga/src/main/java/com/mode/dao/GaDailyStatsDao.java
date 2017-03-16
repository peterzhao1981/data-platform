package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mode.entity.GaDailyStats;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
public interface GaDailyStatsDao {

    @Select("SELECT * FROM ga_daily_stats WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "runTime", column = "run_time"),
            @Result(property = "ip", column = "ip"),
            @Result(property = "clientId", column = "client_id"),
            @Result(property = "userAgent", column = "user_agent"),
            @Result(property = "geoId", column = "geo_id"),
            @Result(property = "countryCode", column = "country_code"),
            @Result(property = "bounceOut", column = "bounce_out"),
            @Result(property = "revisit", column = "revisit"),
            @Result(property = "status", column = "status")})
    public GaDailyStats getGaDailyStats(Integer id);

    @Select("SELECT * FROM ga_daily_stats WHERE ip = #{ip} and client_id is not null limit 1")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "runTime", column = "run_time"),
            @Result(property = "ip", column = "ip"),
            @Result(property = "clientId", column = "client_id"),
            @Result(property = "userAgent", column = "user_agent"),
            @Result(property = "geoId", column = "geo_id"),
            @Result(property = "countryCode", column = "country_code"),
            @Result(property = "bounceOut", column = "bounce_out"),
            @Result(property = "revisit", column = "revisit"),
            @Result(property = "status", column = "status")})
    public GaDailyStats getGaDailyStatsByIp(String ip);

    @Select("SELECT * FROM ga_daily_stats WHERE date <= #{date} and run_time <= #{current} and " +
            "status is null order by id limit 1 for update")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "runTime", column = "run_time"),
            @Result(property = "ip", column = "ip"),
            @Result(property = "clientId", column = "client_id"),
            @Result(property = "userAgent", column = "user_agent"),
            @Result(property = "geoId", column = "geo_id"),
            @Result(property = "countryCode", column = "country_code"),
            @Result(property = "bounceOut", column = "bounce_out"),
            @Result(property = "revisit", column = "revisit")})
    public GaDailyStats getDailyStatsForUpdate(@Param("current") Long current,
                                               @Param("date") Integer date);

    @Update({"<script>",
            "UPDATE ga_daily_stats ",
            "<set>",
            "<if test='date != null'>date = #{date},</if>",
            "<if test='runTime != null'>run_time = #{runTime},</if>",
            "<if test='ip != null'>ip = #{ip},</if>",
            "<if test='clientId != null'>client_id = #{clientId},</if>",
            "<if test='userAgent != null'>user_agent = #{userAgent},</if>",
            "<if test='geoId != null'>geo_id = #{geoId},</if>",
            "<if test='countryCode != null'>country_code = #{countryCode},</if>",
            "<if test='bounceOut != null'>bounce_out = #{bounceOut},</if>",
            "<if test='revisit != null'>revisit = #{revisit},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"})
    public Integer updateGaDailyStats(GaDailyStats gaDailyStats);

    @Select("select distinct ip from ga_daily_stats where user_agent is null and geo_id is null " +
            "order by id")
    public List<String> getIps();

    @Update({"<script>",
            "UPDATE ga_daily_stats ",
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
