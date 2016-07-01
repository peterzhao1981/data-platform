package com.mode.service;

import com.mode.entity.StatsCountry;
import com.mode.entity.StatsHourlyRequest;
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

    public StatsHourlyRequest getHourUser(Integer date);

    public List<StatsCountry> getStatsCountry();
}
