package com.mode.service.impl;

import com.mode.base.Message;
import com.mode.dao.target.CalendarDao;
import com.mode.dao.target.StatsCountryDao;
import com.mode.dao.target.StatsDailyDao;
import com.mode.dao.target.StatsHourlyRequestDao;
import com.mode.dao.target.StatsMonthlyDao;
import com.mode.dao.target.StatsWeeklyDao;
import com.mode.entity.StatsCountry;
import com.mode.entity.StatsDaily;
import com.mode.entity.StatsHourlyRequest;
import com.mode.entity.StatsMonthly;
import com.mode.entity.StatsWeekly;
import com.mode.exception.ModeException;
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

    @Autowired
    private StatsHourlyRequestDao statsHourlyRequestDao;

    @Autowired
    private StatsCountryDao statsCountryDao;

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
                if (l == null || l.isEmpty()) {
                    throw new ModeException(Message.NO_MORE_DATA);
                }
                return l;
            } else if (type == 5) {
                List<StatsMonthly> l = statsMonthlyDao.listMonthlyActivityUser(startDate, endDate);
                if (l == null || l.isEmpty()) {
                    throw new ModeException(Message.NO_MORE_DATA);
                }
                return l;
            }
        if (list == null || list.isEmpty()) {
            throw new ModeException(Message.NO_MORE_DATA);
        }
        return list;
    }

    @Override
    public StatsHourlyRequest getHourUser(Integer date) {
        StatsHourlyRequest statsHourlyRequest = new StatsHourlyRequest();
        statsHourlyRequest = statsHourlyRequestDao.getStatsHourlyRequest(date);
        if (statsHourlyRequest == null) {
            throw new ModeException(Message.NO_MORE_DATA);
        }
        return statsHourlyRequest;
    }

    @Override
    public List<StatsCountry> getStatsCountry() {
        List<StatsCountry> list = statsCountryDao.listCountryUser();
        if (list == null || list.isEmpty()) {
            throw new ModeException(Message.NO_MORE_DATA);
        }
        return list;
    }
}
