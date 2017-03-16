package com.mode.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mode.dao.GaDailyProcessDao;
import com.mode.dao.GaDailyStatsDao;
import com.mode.entity.GaDailyStats;
import com.mode.util.CommonParaGenerator;
import com.mode.util.Status;

/**
 * Created by zhaoweiwei on 17/3/16.
 */
@Service
public class GaAppDailyStatsService {

    @Autowired
    private GaDailyStatsDao dailyStatsDao;

    @Autowired
    private GaDailyProcessDao gaDailyProcessDao;

    @Autowired
    private CommonParaGenerator commonParaGenerator;

    @Autowired
    private DateFormat sdf;

    @Transactional
    public GaDailyStats getGaDailyStats() {
        long now = System.currentTimeMillis();

        GaDailyStats gaDailyStats = dailyStatsDao.getDailyStatsForUpdate(
                now, Integer.valueOf(sdf.format(System.currentTimeMillis())));
        if (gaDailyStats != null) {
            if (gaDailyStats.getRevisit() == true
                    && StringUtils.isEmpty(gaDailyStats.getClientId())) {
                GaDailyStats gaDailyStatsExist = dailyStatsDao.getGaDailyStatsByIp(gaDailyStats
                        .getIp());
                gaDailyStats.setClientId(gaDailyStatsExist.getClientId());

            }

            if (StringUtils.isEmpty(gaDailyStats.getClientId())) {
                gaDailyStats.setClientId(commonParaGenerator.clientId());
            }

            if (StringUtils.isEmpty(gaDailyStats.getUserAgent()) || StringUtils.isEmpty(gaDailyStats.getGeoId()))   {
                String userAgent = commonParaGenerator.getUserAgent();
                String geoId = commonParaGenerator.getGeoId();
                gaDailyStats.setUserAgent(userAgent);
                gaDailyStats.setGeoId(geoId);
            }


            System.out.println(Thread.currentThread() + " : Get id -> " + gaDailyStats.getId());
            gaDailyStats.setStatus(Status.DONE);
            dailyStatsDao.updateGaDailyStats(gaDailyStats);
        }
        return gaDailyStats;

    }

    public static void main(String[] args) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Integer d = Integer.valueOf(sdf.format(System.currentTimeMillis()));
            System.out.println(d);
        } catch (Exception e) {

        }
    }
}
