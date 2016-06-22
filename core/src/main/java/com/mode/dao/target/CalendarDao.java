package com.mode.dao.target;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mode.entity.Calendar;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
public interface CalendarDao {

    /**
     * List calendars from starDate to endDate.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "SELECT * FROM md_calendar ",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND date > #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND date < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "weekend", column = "weekend"),
            @Result(property = "month", column = "month"),
            @Result(property = "quarter", column = "quarter"),
            @Result(property = "year", column = "year"),
            @Result(property = "startTs", column = "start_ts"),
            @Result(property = "endTs", column = "end_ts")})
    public List<Calendar> listCalendars(@Param("startDate") Integer startDate,
                                        @Param("endDate") Integer endDate);

    /**
     * Get calendar by date.
     *
     * @param date
     * @return
     */
    @Select({
            "<script>",
            "SELECT * FROM md_calendar ",
            "where date = #{date}",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "weekend", column = "weekend"),
            @Result(property = "month", column = "month"),
            @Result(property = "quarter", column = "quarter"),
            @Result(property = "year", column = "year"),
            @Result(property = "startTs", column = "start_ts"),
            @Result(property = "endTs", column = "end_ts")})
    public Calendar getCalendar(Integer date);


    /**
     * List weekends from startDate to endDate.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "SELECT distinct weekend FROM md_calendar ",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND weekend > #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND weekend < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    public List<Integer> listWeekends(@Param("startDate") Integer startDate,
                                      @Param("endDate") Integer endDate);


    /**
     * List months from startDate to endDate.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "SELECT distinct month FROM md_calendar ",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND month > #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND month < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    public List<Integer> listMonths(@Param("startDate") Integer startDate,
                                    @Param("endDate") Integer endDate);
}
