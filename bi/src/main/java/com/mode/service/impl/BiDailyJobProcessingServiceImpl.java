package com.mode.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mode.base.AppConfig;
import com.mode.dao.ElasticSearchDao;
import com.mode.dao.source.UserDao;
import com.mode.dao.target.CalendarDao;
import com.mode.dao.target.StatsDailyDao;
import com.mode.dao.target.StatsHourlyRequestDao;
import com.mode.dao.target.StatsMonthlyDao;
import com.mode.dao.target.StatsWeeklyDao;
import com.mode.entity.Calendar;
import com.mode.entity.StatsDaily;
import com.mode.entity.StatsHourlyRequest;
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
    private StatsHourlyRequestDao statsHourlyRequestDao;

    @Autowired
    private ElasticSearchDao elasticSearchDao;

    @Autowired
    private CalendarDao calendarDao;

    private final String STATS_NEW_USER_COL_NAME = "new_user";

    private final String STATS_NEW_USER_FB_COL_NAME = "new_user_fb";

    private final String STATS_NEW_USER_YT_COL_NAME = "new_user_yt";

    private final String STATS_NEW_USER_INS_COL_NAME = "new_user_ins";

    private final String STATS_TOTAL_USER_COL_NAME = "total_user";

    private final String STATS_ACTIVE_USER_COL_NAME = "active_user";

    private final String STATS_ORDER_COL_NAME = "order";

    private final String STATS_TOTAL_ORDER_COL_NAME = "total_order";

    private final String STATS_GMV_COL_NAME = "gmv";

    private final String STATS_TOTAL_GMV_COL_NAME = "total_gmv";

    private final String STATS_HOURLY_REQUEST_COL_NAME = "h1";

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
        processCountry();
        processHourlyRequest(endDate);
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
        try {
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
                Integer insCount = userDao.countUsers(AppConfig.USER_SOURCE_INSTAGRAM, startTs,
                        endTs);
                Integer totalCount = userDao.countUsers(null, null, null);
                StatsDaily statsDaily = new StatsDaily();
                statsDaily.setDate(date);
                statsDaily.setNewUser(count);
                statsDaily.setNewUserFb(fbCount);
                statsDaily.setNewUserYt(ytCount);
                statsDaily.setNewUserIns(insCount);
                statsDaily.setTotalUser(totalCount);
                statsDailyDao.updateStatsDaily(statsDaily);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process order including daily order count, total order count.
     *
     * @param endDate
     */
    private void processOrder(Integer endDate) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process gmv including daily gmv value, total gmv value.
     *
     * @param endDate
     */
    private void processGmv(Integer endDate) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process DAU, WAU, MAU
     *
     * @param endDate
     */
    private void processActiveUser(Integer endDate, String type) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Process user country distribution.
     *
     */
    private void processCountry() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process hourly request.
     *
     * @param endDate
     */
    private void processHourlyRequest(Integer endDate) {
        try {
            Integer lastProcessedDate = statsHourlyRequestDao.getLastProcessedDate(null);
            if (lastProcessedDate == null) {
                lastProcessedDate = AppConfig.LAST_PROCESSED_DAILY_DATE;
            }

            // Get all dates from lastestDate to endDate
            List<Calendar> calendars = calendarDao.listCalendars(lastProcessedDate, endDate);
            for (Calendar calendar : calendars) {
                StatsHourlyRequest statsHourlyRequest = new StatsHourlyRequest();
                statsHourlyRequest.setDate(calendar.getDate());
                statsHourlyRequestDao.createStatsHourlyRequest(statsHourlyRequest);
            }

            List<Integer> dates = statsHourlyRequestDao.listToBeProcessedDates
                    (STATS_HOURLY_REQUEST_COL_NAME, endDate);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

            for (Integer date : dates) {
                Calendar calendar = calendarDao.getCalendar(date);
                List<Long> countList = elasticSearchDao.getRequsetCountByHour(calendar.getStartTs(),
                        calendar.getEndTs());
                if (countList.size() == 24) {
                    StatsHourlyRequest statsHourlyRequest = new StatsHourlyRequest();
                    statsHourlyRequest.setDate(date);
                    statsHourlyRequest.setH1(countList.get(0).intValue());
                    statsHourlyRequest.setH2(countList.get(1).intValue());
                    statsHourlyRequest.setH3(countList.get(2).intValue());
                    statsHourlyRequest.setH4(countList.get(3).intValue());
                    statsHourlyRequest.setH5(countList.get(4).intValue());
                    statsHourlyRequest.setH6(countList.get(5).intValue());
                    statsHourlyRequest.setH7(countList.get(6).intValue());
                    statsHourlyRequest.setH8(countList.get(7).intValue());
                    statsHourlyRequest.setH9(countList.get(8).intValue());
                    statsHourlyRequest.setH10(countList.get(9).intValue());
                    statsHourlyRequest.setH11(countList.get(10).intValue());
                    statsHourlyRequest.setH12(countList.get(11).intValue());
                    statsHourlyRequest.setH13(countList.get(12).intValue());
                    statsHourlyRequest.setH14(countList.get(13).intValue());
                    statsHourlyRequest.setH15(countList.get(14).intValue());
                    statsHourlyRequest.setH16(countList.get(15).intValue());
                    statsHourlyRequest.setH17(countList.get(16).intValue());
                    statsHourlyRequest.setH18(countList.get(17).intValue());
                    statsHourlyRequest.setH19(countList.get(18).intValue());
                    statsHourlyRequest.setH20(countList.get(19).intValue());
                    statsHourlyRequest.setH21(countList.get(20).intValue());
                    statsHourlyRequest.setH22(countList.get(21).intValue());
                    statsHourlyRequest.setH23(countList.get(22).intValue());
                    statsHourlyRequest.setH24(countList.get(23).intValue());

                    statsHourlyRequestDao.updateStatsHourlyRequest(statsHourlyRequest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
//        Pattern p = Pattern.compile("(\\w+)(\\w+)");
//        Matcher matcher = p.matcher("onecattwocatsintheyard");
//        System.out.println(matcher.groupCount());
//        while(matcher.find()) {
//            System.out.println(matcher.group(2));
//        }


        java.text.DecimalFormat   df=new   java.text.DecimalFormat("##.##");
        float d = (float)  0 / 3940;

        System.out.println(String.format("%.2f%%", d));

        System.out.println(101 * 100 / 3980);
        System.out.println(d);
        System.out.println(df.format(d));


    }
}
