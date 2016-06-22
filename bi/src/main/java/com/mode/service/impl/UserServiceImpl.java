package com.mode.service.impl;

import com.mode.dao.target.CalendarDao;
import com.mode.dao.target.StatsDailyDao;
import com.mode.entity.StatsDaily;
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

    @Override
    public List<StatsDaily> listUserRegister(Integer startDate, Integer endDate, Integer type) {
        List<StatsDaily> list = new ArrayList<StatsDaily>();
        try {
            if (type == 1) {
                list = statsDailyDao.listStatsDailys(startDate, endDate, null, null, null);
                return list;
            } else if (type == 2){
                list = statsDailyDao.listStatsWeekly(startDate, endDate);
            }
        } catch (Exception e) {

        }
        return list;
    }

}
