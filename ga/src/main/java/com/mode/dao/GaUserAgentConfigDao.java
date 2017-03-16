package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

/**
 * Created by zhaoweiwei on 17/2/7.
 */
public interface GaUserAgentConfigDao {

    @Select("select user_agent from ga_user_agent_config")
    public List<String> getUserAgents();
}
