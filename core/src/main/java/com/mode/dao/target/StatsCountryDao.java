package com.mode.dao.target;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.mode.entity.StatsCountry;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zhaoweiwei on 16/6/23.
 */
public interface StatsCountryDao {

    @Insert({"<script>",
            "insert into md_stats_country (country, total) ",
            "values ",
            "<foreach collection='statsCountryList' item='statsCountry' index='index' ",
            "open='(' separator='),(' close=')'>",
            "#{statsCountry.country}, #{statsCountry.total}",
            "</foreach>",
            "</script>"})
    public Integer createStatsCountryBatch(@Param("statsCountryList") List<StatsCountry>
                                                       statsCountryList);


    @Delete("truncate table md_stats_country")
    public Integer truncateStatsCountry();

    @Select({
            "<script>",
            "select * from md_stats_country ",
            "</script>"
    })
    @Results({
            @Result(property = "country", column = "country"),
            @Result(property = "total", column = "total")})
    public List<StatsCountry> listCountryUser();
}
