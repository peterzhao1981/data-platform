package com.mode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

/**
 * Created by zhaoweiwei on 17/2/6.
 */
public interface GaGeoConfigDao {


    @Select("select criteria_id from ga_geo_config where canonical_name like '%California%'" +
            " or canonical_name like '%New York%' and target_type = 'city'")
    public List<String> getMainState();

    @Select("select criteria_id from ga_geo_config where canonical_name not like '%California%'" +
            " and canonical_name not like '%New York%' and target_type = 'city'")
    public List<String> getOtherState();
}
