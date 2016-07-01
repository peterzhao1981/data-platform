package com.mode.dao.source;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by kang on 2016/6/30.
 */
public interface UserActionLogDao {

    @Select({
            "<script>",
            "select count(id) from md_user_action_log where object_type = 'post'",
            " and action = 'view' and object_value = #{postId}",
            "</script>"
    })
    public Integer countPostViews(@Param("postId") Integer postId);
}
