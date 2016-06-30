package com.mode.service;

import com.mode.entity.StatsDaily;
import com.mode.entity.StatsMonthly;
import com.mode.entity.StatsWeekly;
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
    public List<StatsDaily> listUserRegister(Integer startDate, Integer endDate, Integer type);

    /**
     * List weekly active user
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<StatsWeekly> listWeeklyActivityUser(Integer startDate, Integer endDate);

    /**
     * List monthly active user
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<StatsMonthly> listMonthlyActivityUser(Integer startDate, Integer endDate);
}
