package com.mode.dao.source;

import com.mode.entity.Post;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by kang on 2016/6/30.
 */
public interface PostDao {

    @Select({
            "<script>",
            "select * from md_post",
            "<where>",
            "<if test = 'startDate != null'> <![CDATA[ AND ctime >= #{startDate} ]]> </if>",
            "<if test = 'endDate != null'> <![CDATA[ AND ctime <= #{endDate} ]]> </if>",
            "</where>",
            "order by ctime",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "favors", column = "favors"),
            @Result(property = "comments", column = "comments"),
            @Result(property = "shares", column = "shares"),
    })
    public List<Post> getStatsPost(@Param("startDate") Long startDate,
                                   @Param("endDate") Long endDate);

}
