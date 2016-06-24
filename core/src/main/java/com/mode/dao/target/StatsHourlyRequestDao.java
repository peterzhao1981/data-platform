package com.mode.dao.target;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.mode.entity.StatsHourlyRequest;

/**
 * Created by zhaoweiwei on 16/6/23.
 */
public interface StatsHourlyRequestDao {

    /**
     * Create statsHourlyRequest.
     *
     * @param statsHourlyRequest
     * @return
     */
    @Insert("INSERT INTO md_stats_hourly_request (date, h1, h2, h3, h4, h5, h6, h7, h8, " +
            "h9, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23, h24) " +
            "VALUES (#{date}, #{h1}, #{h2}, #{h3}, #{h4}, #{h5}, #{h6}, #{h7}, #{h8}, #{h9}, " +
            "#{h10}, #{h11}, #{h12}, #{h13}, #{h14}, #{h15}, #{h16}, #{h17}, #{h18}, #{h19}, " +
            "#{h20}, #{h21}, #{h22}, #{h23}, #{h24})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Integer.class)
    public Integer createStatsHourlyRequest(StatsHourlyRequest statsHourlyRequest);


    /**
     * Update statsHourlyRequest.
     *
     * @param statsHourlyRequest
     * @return
     */
    @Update({"<script>",
            "UPDATE md_stats_hourly_request ",
            "<set>",
            "<if test='h1 != null'> h1 = #{h1}, </if>",
            "<if test='h2 != null'> h2 = #{h2}, </if>",
            "<if test='h3 != null'> h3 = #{h3}, </if>",
            "<if test='h4 != null'> h4 = #{h4}, </if>",
            "<if test='h5 != null'> h5 = #{h5}, </if>",
            "<if test='h6 != null'> h6 = #{h6}, </if>",
            "<if test='h7 != null'> h7 = #{h7}, </if>",
            "<if test='h8 != null'> h8 = #{h8}, </if>",
            "<if test='h9 != null'> h9 = #{h9}, </if>",
            "<if test='h10 != null'> h10 = #{h10}, </if>",
            "<if test='h11 != null'> h11 = #{h11}, </if>",
            "<if test='h12 != null'> h12 = #{h12}, </if>",
            "<if test='h13 != null'> h13 = #{h13}, </if>",
            "<if test='h14 != null'> h14 = #{h14}, </if>",
            "<if test='h15 != null'> h15 = #{h15}, </if>",
            "<if test='h16 != null'> h16 = #{h16}, </if>",
            "<if test='h17 != null'> h17 = #{h17}, </if>",
            "<if test='h18 != null'> h18 = #{h18}, </if>",
            "<if test='h19 != null'> h19 = #{h19}, </if>",
            "<if test='h20 != null'> h20 = #{h20}, </if>",
            "<if test='h21 != null'> h21 = #{h21}, </if>",
            "<if test='h22 != null'> h22 = #{h22}, </if>",
            "<if test='h23 != null'> h23 = #{h23}, </if>",
            "<if test='h24 != null'> h24 = #{h24}, </if>",
            "</set>",
            "WHERE date = #{date}",
            "</script>"})
    public Integer updateStatsHourlyRequest(StatsHourlyRequest statsHourlyRequest);



    /**
     * Get statsHourlyRequest by date.
     *
     * @param date
     * @return
     */
    @Select({
            "<script>",
            "SELECT * FROM md_stats_hourly_request ",
            "where date = #{date}",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "h1", column = "h1"),
            @Result(property = "h2", column = "h2"),
            @Result(property = "h3", column = "h3"),
            @Result(property = "h4", column = "h4"),
            @Result(property = "h5", column = "h5"),
            @Result(property = "h6", column = "h6"),
            @Result(property = "h7", column = "h7"),
            @Result(property = "h8", column = "h8"),
            @Result(property = "h9", column = "h9"),
            @Result(property = "h10", column = "h10"),
            @Result(property = "h11", column = "h11"),
            @Result(property = "h12", column = "h12"),
            @Result(property = "h13", column = "h13"),
            @Result(property = "h14", column = "h14"),
            @Result(property = "h15", column = "h15"),
            @Result(property = "h16", column = "h16"),
            @Result(property = "h17", column = "h17"),
            @Result(property = "h18", column = "h18"),
            @Result(property = "h19", column = "h19"),
            @Result(property = "h20", column = "h20"),
            @Result(property = "h21", column = "h21"),
            @Result(property = "h22", column = "h22"),
            @Result(property = "h23", column = "h23"),
            @Result(property = "h24", column = "h24")})
    public StatsHourlyRequest getStatsHourlyRequest(Integer date);


    /**
     * Get last processed date.
     *
     * @param columnName
     * @return
     */
    @Select({
            "<script>",
            "SELECT max(date) FROM md_stats_hourly_request ",
            "<where>",
            "<if test='columnName != null'>  ${columnName} is not null </if>",
            "</where>",
            "</script>"
    })
    public Integer getLastProcessedDate(String columnName);


    /**
     * List column's to be processed dates.
     *
     * @param columnName
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "SELECT date FROM md_stats_hourly_request ",
            "<where>",
            "<if test='columnName != null'>  ${columnName} is null </if>",
            "<if test='endDate != null'>  <![CDATA[ AND date < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    public List<Integer> listToBeProcessedDates(@Param("columnName") String columnName,
                                                @Param("endDate") Integer endDate);

}
