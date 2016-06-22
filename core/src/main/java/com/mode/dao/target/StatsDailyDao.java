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
    @Insert("INSERT INTO md_stats_daily (date, new_reg, new_reg_fb, new_reg_yt, total_reg, " +
            "actives, orders, total_orders, gmv, total_gmv) " +
            "VALUES (#{date}, #{newReg}, #{newRegFb}, #{newRegYt}, #{totalReg}, " +
            "#{actives}, #{orders}, #{totalOrders}, #{gmv}, #{totalGmv})")
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
            "<if test='newReg != null'> new_reg = #{newReg}, </if>",
            "<if test='newRegFb != null'> new_reg_fb = #{newRegFb}, </if>",
            "<if test='newRegYt != null'> new_reg_yt = #{newRegYt}, </if>",
            "<if test='totalReg != null'> total_reg = #{totalReg}, </if>",
            "<if test='actives != null'> actives = #{actives}, </if>",
            "<if test='orders != null'> orders = #{orders}, </if>",
            "<if test='totalOrders != null'> total_orders = #{totalOrders}, </if>",
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
            "<if test='newReg != null'> AND new_reg = #{newReg} </if>",
            "<if test='newRegFb != null'> AND new_reg_fb = #{newRegFb} </if>",
            "<if test='newRegYt != null'> AND new_reg_yt = #{newRegYt} </if>",
            "<if test='totalReg != null'> AND total_reg = #{totalReg} </if>",
            "<if test='actives != null'> AND actives = #{actives} </if>",
            "<if test='orders != null'> AND orders = #{orders} </if>",
            "<if test='totalOrders != null'> AND total_orders = #{totalOrders} </if>",
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
            @Result(property = "newReg", column = "new_reg"),
            @Result(property = "newRegFb", column = "new_reg_fb"),
            @Result(property = "newRegYt", column = "new_reg_yt"),
            @Result(property = "totalReg", column = "total_reg"),
            @Result(property = "actives", column = "actives"),
            @Result(property = "orders", column = "orders"),
            @Result(property = "totalOrders", column = "total_orders"),
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
            @Result(property = "newReg", column = "new_reg"),
            @Result(property = "newRegFb", column = "new_reg_fb"),
            @Result(property = "newRegYt", column = "new_reg_yt"),
            @Result(property = "totalReg", column = "total_reg"),
            @Result(property = "actives", column = "actives"),
            @Result(property = "orders", column = "orders"),
            @Result(property = "totalOrders", column = "total_orders"),
            @Result(property = "gmv", column = "gmv"),
            @Result(property = "totalGmv", column = "total_gmv")})
    public List<StatsDaily> listStatsDailys(@Param("startDate") Integer startDate,
                                            @Param("endDate") Integer endDate,
                                            @Param("orderByClause") String orderByClause,
                                            @Param("limit") Integer limit,
                                            @Param("offset") Integer offset);



    @Select({
            "<script>",
            "SELECT max(date) FROM md_stats_daily ",
            "<where>",
            "<if test='columnName != null'>  ${columnName} is not null </if>",
            "</where>",
            "</script>"
    })
    public Integer getLastestDate(String columnName);


    @Select({
            "<script>",
            "SELECT date FROM md_stats_daily ",
            "<where>",
            "<if test='columnName != null'>  ${columnName} is null </if>",
            "<if test='endDate != null'>  <![CDATA[ AND date <= #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    public List<Integer> getToBeProcessedDate(String columnName, Integer endDate);

    @Select({
            "<script>",
            "select b.weekend as date,sum(a.new_reg) as newReg,sum(a.new_reg_fb) as newRegFb,sum" +
                    "(a.new_reg_yt) as newRegYt," +
                    "sum(orders) as orders,sum(gmv) as gmv from md_stats_daily a  inner join " +
                    "md_calendar b on a" +
                    ".date=b.date group by b.weekend",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND a.date >= #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND a.date <= #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "newReg", column = "new_reg"),
            @Result(property = "newRegFb", column = "new_reg_fb"),
            @Result(property = "newRegYt", column = "new_reg_yt"),
            @Result(property = "totalReg", column = "total_reg"),
            @Result(property = "actives", column = "actives"),
            @Result(property = "orders", column = "orders"),
            @Result(property = "totalOrders", column = "total_orders"),
            @Result(property = "gmv", column = "gmv"),
            @Result(property = "totalGmv", column = "total_gmv")})
    public List<StatsDaily> listStatsWeekly(@Param("startDate") Integer startDate,
                                            @Param("endDate") Integer endDate);


}
