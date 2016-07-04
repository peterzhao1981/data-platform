package com.mode.service;

import com.mode.entity.Post;
import com.mode.entity.StatsCountry;
import java.util.List;

/**
 * Created by kang on 2016/6/22.
 */
public interface UserService {

    /**
     * List daily user register ,order , gmv
     *
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    public List<? extends Object> listStatsInfo(Integer startDate, Integer endDate, Integer type);

    /**
     * List Stats country by users
     *
     * @return
     */
    public List<StatsCountry> getStatsCountry();

    /**
     * List post view, like, comment etc
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Post> getStatsPost(Long startDate, Long endDate);
}
