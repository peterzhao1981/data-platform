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
import com.mode.dao.target.StatsMonthlyDao;
import com.mode.dao.target.StatsWeeklyDao;
import com.mode.entity.Calendar;
import com.mode.entity.StatsDaily;
import com.mode.entity.StatsMonthly;
import com.mode.entity.StatsWeekly;
import com.mode.service.BiDailyJobProcessingService;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
@Service
public class BiDailyJobProcessingServiceImpl implements BiDailyJobProcessingService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StatsDailyDao statsDailyDao;

    @Autowired
    private StatsWeeklyDao statsWeeklyDao;

    @Autowired
    private StatsMonthlyDao statsMonthlyDao;

    @Autowired
    private CalendarDao calendarDao;

    private final String STATS_NEW_USER_COL_NAME = "new_user";

    private final String STATS_NEW_USER_FB_COL_NAME = "new_user_fb";

    private final String STATS_NEW_USER_YT_COL_NAME = "new_user_yt";

    private final String STATS_TOTAL_USER_COL_NAME = "total_user";

    private final String STATS_ACTIVE_USER_COL_NAME = "active_user";

    private final String STATS_ORDER_COL_NAME = "order";

    private final String STATS_TOTAL_ORDER_COL_NAME = "total_order";

    private final String STATS_GMV_COL_NAME = "gmv";

    private final String STATS_TOTAL_GMV_COL_NAME = "total_gmv";

    @Override
    public void process() {
        long now = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // Process endDate is yesterday
        Integer endDate = Integer.parseInt(df.format(now));

        processDailyStats(endDate);
        processWeeklyStats(endDate);
        processMonthlyStats(endDate);
    }

    /**
     * Process daily stats.
     *
     * @param endDate
     */
    private void processDailyStats(Integer endDate) {
        Integer lastProcessedDate = statsDailyDao.getLastProcessedDate(null);
        if (lastProcessedDate == null) {
            lastProcessedDate = AppConfig.LAST_PROCESSED_DAILY_DATE;
        }
        // Get all dates from lastestDate to endDate
        List<Calendar> calendars = calendarDao.listCalendars(lastProcessedDate, endDate);
        for (Calendar calendar : calendars) {
            StatsDaily statsDaily = new StatsDaily();
            statsDaily.setDate(calendar.getDate());
            statsDailyDao.createStatsDaily(statsDaily);
        }
        processNewUser(endDate);
        processOrder(endDate);
        processGmv(endDate);
        processActiveUser(endDate, AppConfig.STATS_TYPE_DAILY);
    }

    /**
     * Process weekly stats.
     *
     * @param endDate
     */
    private void processWeeklyStats(Integer endDate) {
        Integer lastProcessedDate = statsWeeklyDao.getLastProcessedDate(null);
        if (lastProcessedDate == null) {
            lastProcessedDate = AppConfig.LAST_PROCESSED_WEEKLY_DATE;
        }
        Integer endWeekend = calendarDao.getCalendar(endDate).getWeekend();
        // Get all weekends from lastProcssedDate to endDate
        List<Integer> weekends = calendarDao.listWeekends(lastProcessedDate, endWeekend);
        for (Integer weekend : weekends) {
            StatsWeekly statsWeekly = new StatsWeekly();
            statsWeekly.setDate(weekend);
            statsWeeklyDao.createStatsWeekly(statsWeekly);
        }
        processActiveUser(endWeekend, AppConfig.STATS_TYPE_WEEKLY);
    }

    /**
     * Process monthly stats.
     *
     * @param endDate
     */
    private void processMonthlyStats(Integer endDate) {
        Integer lastProcessedDate = statsMonthlyDao.getLastProcessedDate(null);
        if (lastProcessedDate == null) {
            lastProcessedDate = AppConfig.LAST_PROCESSED_MONTHLY_DATE;
        }
        Integer endMonth = calendarDao.getCalendar(endDate).getMonth();
        // Get all months from lastProcessedDate to endDate
        List<Integer> months = calendarDao.listMonths(lastProcessedDate, endMonth);
        for (Integer month : months) {
            StatsMonthly statsMonthly = new StatsMonthly();
            statsMonthly.setDate(month);
            statsMonthlyDao.createStatsMontyly(statsMonthly);
        }
        processActiveUser(endMonth, AppConfig.STATS_TYPE_MONTHLY);
    }


    /**
     * Process new user including daily new user count, daily facebook new
     * user count, daily youtube new user count, total user count.
     *
     * @param endDate
     */
    private void processNewUser(Integer endDate) {
        List<Integer> dates = statsDailyDao.listToBeProcessedDates(STATS_NEW_USER_COL_NAME,
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
    private void processActiveUser(Integer endDate, String type) {
        if (AppConfig.STATS_TYPE_DAILY.equalsIgnoreCase(type)) {
            // Process DAU
            List<Integer> dates = statsDailyDao.listToBeProcessedDates
                    (STATS_ACTIVE_USER_COL_NAME, endDate);
            for (Integer date : dates) {
                System.out.println(date);
            }
        } else if (AppConfig.STATS_TYPE_WEEKLY.equalsIgnoreCase(type)) {
            // Process WAU
            List<Integer> weekends = statsWeeklyDao.listToBeProcessedDates
                    (STATS_ACTIVE_USER_COL_NAME, endDate);
            for (Integer weekend : weekends) {
                System.out.println(weekend);
            }
        } else if (AppConfig.STATS_TYPE_MONTHLY.equalsIgnoreCase(type)) {
            // Process MAU
            List<Integer> months = statsMonthlyDao.listToBeProcessedDates
                    (STATS_ACTIVE_USER_COL_NAME, endDate);
            for (Integer month : months) {
                System.out.println(month);
            }

        }

    }

//    public static void main(String[] args) {
//        BiDailyProcessingServiceImpl biDailyProcessingService = new BiDailyProcessingServiceImpl();
//        biDailyProcessingService.process();
//    }
}
