package com.mode.dao.target;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.mode.entity.StatsDaily;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
public interface StatsDailyDao {

    /**
     * Create statsDaily.
     *
     * @param statsDaily
     * @return
     */
    @Insert("INSERT INTO md_stats_daily (date, new_user, new_user_fb, new_user_yt, total_user, " +
            "active_user, `order`, total_order, gmv, total_gmv) " +
            "VALUES (#{date}, #{newUser}, #{newUserFb}, #{newUserYt}, #{totalUser}, " +
            "#{activeUser}, #{order}, #{totalOrder}, #{gmv}, #{totalGmv})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Integer.class)
    public Integer createStatsDaily(StatsDaily statsDaily);


    /**
     * Update statsDaily.
     *
     * @param statsDaily
     * @return
     */
    @Update({"<script>",
            "UPDATE md_stats_daily ",
            "<set>",
            "<if test='newUser != null'> new_user = #{newUser}, </if>",
            "<if test='newUserFb != null'> new_user_fb = #{newUserFb}, </if>",
            "<if test='newUserYt != null'> new_user_yt = #{newUserYt}, </if>",
            "<if test='totalUser != null'> total_user = #{totalUser}, </if>",
            "<if test='activeUser != null'> active_user = #{activeUser}, </if>",
            "<if test='order != null'> order = #{order}, </if>",
            "<if test='totalOrder != null'> total_order = #{totalOrder}, </if>",
            "<if test='gmv != null'> gmv = #{gmv}, </if>",
            "<if test='totalGmv != null'> total_gmv = #{totalGmv}, </if>",
            "</set>",
            "WHERE date = #{date}",
            "</script>"})
    public Integer updateStatsDaily(StatsDaily statsDaily);


    /**
     * Get statsDaily.
     *
     * @param statsDaily
     * @return
     */
    @Select({
            "<script>",
            "SELECT * FROM md_stats_daily ",
            "<where>",
            "<choose>",
            "<when test='date != null'> date = #{date} </when>",
            "<otherwise>",
            "<if test='id != null'> AND id = #{id} </if>",
            "<if test='newUser != null'> AND new_user = #{newUser} </if>",
            "<if test='newUserFb != null'> AND new_user_fb = #{newUserFb} </if>",
            "<if test='newUserYt != null'> AND new_user_yt = #{newUserYt} </if>",
            "<if test='totalUser != null'> AND total_user = #{totalUser} </if>",
            "<if test='activeUser != null'> AND active_user = #{activeUser} </if>",
            "<if test='order != null'> AND order = #{order} </if>",
            "<if test='totalOrder != null'> AND total_order = #{totalOrder} </if>",
            "<if test='gmv != null'> AND gmv = #{gmv} </if>",
            "<if test='totalGmv != null'> AND total_gmv = #{totalGmv} </if>",
            "</otherwise>",
            "</choose>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "newUser", column = "new_user"),
            @Result(property = "newUserFb", column = "new_user_fb"),
            @Result(property = "newUserYt", column = "new_user_yt"),
            @Result(property = "totalUser", column = "total_user"),
            @Result(property = "activeUser", column = "active_user"),
            @Result(property = "order", column = "order"),
            @Result(property = "totalOrder", column = "total_order"),
            @Result(property = "gmv", column = "gmv"),
            @Result(property = "totalGmv", column = "total_gmv")})
    public StatsDaily getStatsDaily(StatsDaily statsDaily);


    /**
     * List StatsDailys.
     *
     * @param startDate
     * @param endDate
     * @param orderByClause
     * @param limit
     * @param offset
     * @return
     */
    @Select({
            "<script>",
            "SELECT * FROM md_stats_daily ",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND date >= #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND date <= #{endDate} ]]> </if>",
            "</where>",
            "<if test='orderByClause != null'> order by ${orderByClause} </if>",
            "<if test='limit != null'> limit #{limit} </if>",
            "<if test='offset != null'> offset #{offset} </if>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "newUser", column = "new_user"),
            @Result(property = "newUserFb", column = "new_user_fb"),
            @Result(property = "newUserYt", column = "new_user_yt"),
            @Result(property = "totalUser", column = "total_user"),
            @Result(property = "activeUser", column = "active_user"),
            @Result(property = "order", column = "order"),
            @Result(property = "totalOrder", column = "total_order"),
            @Result(property = "gmv", column = "gmv"),
            @Result(property = "totalGmv", column = "total_gmv")})
    public List<StatsDaily> listStatsDailys(@Param("startDate") Integer startDate,
                                            @Param("endDate") Integer endDate,
                                            @Param("orderByClause") String orderByClause,
                                            @Param("limit") Integer limit,
                                            @Param("offset") Integer offset);


    /**
     * Get last processed date.
     *
     * @param columnName
     * @return
     */
    @Select({
            "<script>",
            "SELECT max(date) FROM md_stats_daily ",
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
            "SELECT date FROM md_stats_daily ",
            "<where>",
            "<if test='columnName != null'>  ${columnName} is null </if>",
            "<if test='endDate != null'>  <![CDATA[ AND date < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    public List<Integer> listToBeProcessedDates(@Param("columnName") String columnName,
                                                @Param("endDate") Integer endDate);

    /**
     * List daily user register,order,gmv
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "select b.weekend as date,sum(a.new_user) as newUser,sum(a.new_user_fb) as newUserFb,sum(a.new_user_yt) as newUserYt," +
             "sum(`order`) as `order`,sum(gmv) as gmv,a.total_user,a.total_order,a.total_gmv from" +
                    " md_stats_daily a  inner join " +
             "md_calendar b on a.date=b.date ",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND a.date >= #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND a.date <= #{endDate} ]]> </if>",
            "</where>",
            "group by b.weekend ",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "newUser", column = "new_user"),
            @Result(property = "newUserFb", column = "new_user_fb"),
            @Result(property = "newUserYt", column = "new_user_yt"),
            @Result(property = "totalUser", column = "total_user"),
            @Result(property = "activeUser", column = "active_user"),
            @Result(property = "order", column = "order"),
            @Result(property = "totalOrder", column = "total_order"),
            @Result(property = "gmv", column = "gmv"),
            @Result(property = "totalGmv", column = "total_gmv")})
    public List<StatsDaily> listStatsWeekly(@Param("startDate") Integer startDate,
                                            @Param("endDate") Integer endDate);


    /**
     * List monthly user register,order,gmv
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "select b.month as date,sum(a.new_user) as newUser,sum(a.new_user_fb) as newUserFb," +
                    "sum(a.new_user_yt) as newUserYt," +
                    "sum(`order`) as `order`,sum(gmv) as gmv,a.total_user,a.total_order,a.total_gmv from" +
                    " md_stats_daily a  inner join " +
                    "md_calendar b on a.date=b.date ",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND a.date >= #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND a.date <= #{endDate} ]]> </if>",
            "</where>",
            "group by b.month ",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "newUser", column = "new_user"),
            @Result(property = "newUserFb", column = "new_user_fb"),
            @Result(property = "newUserYt", column = "new_user_yt"),
            @Result(property = "totalUser", column = "total_user"),
            @Result(property = "activeUser", column = "active_user"),
            @Result(property = "order", column = "order"),
            @Result(property = "totalOrder", column = "total_order"),
            @Result(property = "gmv", column = "gmv"),
            @Result(property = "totalGmv", column = "total_gmv")})
    public List<StatsDaily> listStatsMonthly(@Param("startDate") Integer startDate,
                                            @Param("endDate") Integer endDate);


}
