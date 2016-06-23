package com.mode.dao.target;

import com.mode.entity.StatsMonthly;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by zhaoweiwei on 16/6/22.
 */
public interface StatsMonthlyDao {

    /**
     * Create statsMonthly.
     *
     * @param statsMonthly
     * @return
     */
    @Insert("INSERT INTO md_stats_monthly (date, active_user) " +
            "VALUES (#{date}, #{activeUser})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Integer.class)
    public Integer createStatsMontyly(StatsMonthly statsMonthly);


    /**
     * Get last processed date.
     *
     * @param columnName
     * @return
     */
    @Select({
            "<script>",
            "SELECT max(date) FROM md_stats_monthly ",
            "<where>",
            "<if test='columnName != null'>  ${columnName} is not null </if>",
            "</where>",
            "</script>"
    })
    public Integer getLastProcessedDate(String columnName);


    /**
     * List to be processed dates.
     *
     * @param columnName
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "SELECT date FROM md_stats_monthly ",
            "<where>",
            "<if test='columnName != null'>  ${columnName} is null </if>",
            "<if test='endDate != null'>  <![CDATA[ AND date < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    public List<Integer> listToBeProcessedDates(@Param("columnName") String columnName,
                                                @Param("endDate") Integer endDate);


    /**
     * List Monthly active user
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "select * from md_stats_monthly ",
            "<where>",
            "<if test='endDate != null'>  <![CDATA[ AND date >= #{startDate} ]]> </if>",
            "<if test='endDate != null'>  <![CDATA[ AND date < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "activeUser", column = "active_user")})
    public List<StatsMonthly> listMonthlyActivityUser(@Param("startDate") Integer startDate,
                                                      @Param("endDate") Integer endDate);
}
