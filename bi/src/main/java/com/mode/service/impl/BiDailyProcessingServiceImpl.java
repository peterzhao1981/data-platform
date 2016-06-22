package com.mode.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mode.base.AppConfig;
import com.mode.dao.source.UserDao;
import com.mode.dao.target.CalendarDao;
import com.mode.dao.target.StatsDailyDao;
import com.mode.entity.Calendar;
import com.mode.entity.StatsDaily;
import com.mode.service.BiDailyProcessingService;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
@Service
public class BiDailyProcessingServiceImpl implements BiDailyProcessingService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StatsDailyDao statsDailyDao;

    @Autowired
    private CalendarDao calendarDao;

    private final String STATS_DAILY_NEW_USER_COL_NAME = "new_user";

    private final String STATS_DAILY_NEW_USER_FB_COL_NAME = "new_user_fb";

    private final String STATS_DAILY_NEW_USER_YT_COL_NAME = "new_user_yt";

    private final String STATS_DAILY_TOTAL_USER_COL_NAME = "total_user";

    private final String STATS_DAILY_ACTIVE_USER_COL_NAME = "active_user";

    private final String STATS_DAILY_ORDER_COL_NAME = "order";

    private final String STATS_DAILY_TOTAL_ORDER_COL_NAME = "total_order";

    private final String STATS_DAILY_GMV_COL_NAME = "gmv";

    private final String STATS_DAILY_TOTAL_GMV_COL_NAME = "total_gmv";

    @Override
    public void process() {
        long now = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // Process endDate is yesterday
        Integer endDate = Integer.parseInt(df.format(now));

        Integer lastProcessedDate = statsDailyDao.getLastProcessedDate(null);
        if (lastProcessedDate == null) {
            lastProcessedDate = AppConfig.LAST_PROCESSED_DATE;
        }
        // Get all dates from lastestDate to endDate
        List<Calendar> calendars = calendarDao.listCalendars(lastProcessedDate, endDate);
        for (Calendar calendar : calendars) {
            StatsDaily statsDaily = new StatsDaily();
            statsDaily.setDate(calendar.getDate());
            statsDailyDao.createStatsDaily(statsDaily);
        }
        processNewUser(endDate);




    }


    /**
     * Process new user including daily new user count, daily facebook new
     * user count, daily youtube new user count, total user count.
     *
     * @param endDate
     */
    private void processNewUser(Integer endDate) {
        List<Integer> dates = statsDailyDao.listToBeProcessedDates(STATS_DAILY_NEW_USER_COL_NAME,
                endDate);
        for (Integer date : dates) {
//            System.out.println(date);
            Calendar calendar = calendarDao.getCalendar(date);
            Long startTs = calendar.getStartTs();
            Long endTs = calendar.getEndTs();
            Integer count = userDao.countUsers(null, startTs, endTs);
            Integer fbCount = userDao.countUsers(AppConfig.USER_SOURCE_FACEBOOK, startTs, endTs);
            Integer ytCount = userDao.countUsers(AppConfig.USER_SOURCE_YOUTUBE, startTs, endTs);
            Integer totalCount = userDao.countUsers(null, null, null);
            StatsDaily statsDaily = new StatsDaily();
            statsDaily.setDate(date);
            statsDaily.setNewUser(count);
            statsDaily.setNewUserFb(fbCount);
            statsDaily.setNewUserYt(ytCount);
            statsDaily.setTotalUser(totalCount);
            statsDailyDao.updateStatsDaily(statsDaily);
        }
    }

    /**
     * Process order including daily order count, total order count.
     *
     * @param endDate
     */
    private void processOrder(Integer endDate) {

    }

    /**
     * Process gmv including daily gmv value, total gmv value.
     *
     * @param endDate
     */
    private void processGmv(Integer endDate) {

    }

    /**
     * Process DAU, WAU, MAU
     *
     * @param endDate
     */
    private void processActiveUser(Integer endDate) {
        // Process DAU
        List<Integer> dates = statsDailyDao.listToBeProcessedDates(STATS_DAILY_ACTIVE_USER_COL_NAME,
                endDate);
        for (Integer date : dates) {

        }
        // Process WAU


    }

//    public static void main(String[] args) {
//        BiDailyProcessingServiceImpl biDailyProcessingService = new BiDailyProcessingServiceImpl();
//        biDailyProcessingService.process();
//    }
}
