package com.mode.dao2;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zhaoweiwei on 16/9/22.
 */
public interface CalendarDao {
//    @Select({
//            "<script>",
//            "SELECT count(*) FROM md_calendar ",
//            "</script>"
//    })
//    Integer count();

    @Insert("INSERT INTO md_test (`key`, `value`) values (#{a}, #{a})")
    public Integer createTest(int a);

}
