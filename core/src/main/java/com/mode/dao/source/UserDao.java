package com.mode.dao.source;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSessionFactory;

import com.mode.entity.User;

/**
 * Created by zhaoweiwei on 16/6/20.
 */
public interface UserDao {


    @Select({
            "<script>",
            "SELECT count(*) FROM md_user ",
            "<where>",
            "<if test='source != null'> AND source = #{source} </if>",
            "<if test='startTs != null'> <![CDATA[ AND ctime >= #{startTs} ]]> </if>",
            "<if test='endTs != null'> <![CDATA[ AND ctime < #{endTs} ]]> </if>",
            "</where>",
            "</script>"
    })
    public Integer countUsers(@Param("source") String source,
                              @Param("startTs") Long startTs,
                              @Param("endTs") Long endTs);

}
