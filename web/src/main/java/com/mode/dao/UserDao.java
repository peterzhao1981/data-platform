package com.mode.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mode.entity.User;

/**
 * Created by Ben Hu on 2016/7/20.
 */
public interface UserDao {
    /**
     * List the feeds written by the authors who are followed by the user
     *
     * @param email user's Email
     * @return User
     */
    @Select({
            "<script>",
            "SELECT * FROM md_user u",
            "<where>",
            "u.email = #{email}",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "password", column = "password"),
            @Result(property = "reset_password_key", column = "reset_password_key"),
            @Result(property = "last_password_resetTime", column = "last_password_resetTime")})
    User getUserByEmail(@Param("email") String email);

    /**
     * Update userComment
     *
     * @param user
     * @return
     */
    @Update({"<script>",
            "UPDATE md_user ",
            "<set>",
            "<if test='id != null'> id = #{id}, </if>",
            "<if test='password != null'> password = #{password}, </if>",
            "<if test='reset_password_key != null'> reset_password_key = #{resetPasswordKey}, </if>",
            "<if test='last_password_resetTime != null'> last_password_resetTime = #{lastPasswordResetTime}, </if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"})
    Integer updateUser(User user);
}
