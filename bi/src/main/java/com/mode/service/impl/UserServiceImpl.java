package com.mode.service.impl;

import com.mode.dao.target.CalendarDao;
import com.mode.dao.target.StatsDailyDao;
import com.mode.dao.target.StatsMonthlyDao;
import com.mode.dao.target.StatsWeeklyDao;
import com.mode.entity.StatsDaily;
import com.mode.entity.StatsMonthly;
import com.mode.entity.StatsWeekly;
import com.mode.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kang on 2016/6/22.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private StatsDailyDao statsDailyDao;

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private StatsWeeklyDao statsWeeklyDao;

    @Autowired
    private StatsMonthlyDao statsMonthlyDao;

    @Override
    public List<? extends Object> listStatsInfo(Integer startDate, Integer endDate, Integer type) {
        List<StatsDaily> list = new ArrayList<StatsDaily>();
            if (type == 1) {
                list = statsDailyDao.listStatsDailys(startDate, endDate, null, null, null);
                return list;
            } else if (type == 2){
                Integer newStartDate = calendarDao.getWeekFirstDay(startDate);
                Integer newEndDate = calendarDao.getWeekLastDay(endDate);
                list = statsDailyDao.listStatsWeekly(newStartDate, newEndDate);
            } else if (type == 3) {
                Integer newStartDate = calendarDao.getMonthFirstDay(startDate);
                Integer newEndDate = calendarDao.getMonthLastDay(endDate);
                list = statsDailyDao.listStatsMonthly(newStartDate, newEndDate);
            } else if (type == 4) {
                List<StatsWeekly> l = statsWeeklyDao.listWeeklyActivityUser(startDate, endDate);
                return l;
            } else if (type == 5) {
                List<StatsMonthly> l = statsMonthlyDao.listMonthlyActivityUser(startDate, endDate);
                return l;
            }
        return list;
    }
}
