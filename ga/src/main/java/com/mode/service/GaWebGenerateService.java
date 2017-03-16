package com.mode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.mode.dao.GaDailyProcessDao;
import com.mode.dao.GaDailyStatsDao;
import com.mode.dao.GaGeoConfigDao;
import com.mode.dao.GaUserAgentConfigDao;
import com.mode.entity.GaDailyProcess;
import com.mode.entity.GaDailyStats;
import com.mode.entity.Page;
import com.mode.util.CommonParaGenerator;
import com.mode.util.HitGenerator;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
@Service
public class GaWebGenerateService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private GaDailyStatsService gaDailyStatsService;

    @Autowired
    private GaDailyProcessService gaDailyProcessService;

    @Autowired
    private GaDailyProcessDao gaDailyProcessDao;

    @Autowired
    private GaDailyStatsDao gaDailyStatsDao;

    @Autowired
    private CommonParaGenerator commonParaGenerator;

    @Autowired
    private HitGenerator hitGenerator;

    @Value("${application.thread.num}")
    private Integer threadNum;

    public void processDailyStats() {
        try {
            GaDailyStats gaDailyStats = gaDailyStatsService.getGaDailyStats();
            if (gaDailyStats != null) {
                if (gaDailyStats.getBounceOut() == null || gaDailyStats.getBounceOut() == true) {
                    hitGenerator.generate(gaDailyStats.getIp(),
                            gaDailyStats.getClientId(), gaDailyStats.getUserAgent(),
                            gaDailyStats.getGeoId(), commonParaGenerator.getRoot(), null);
                } else {
                    Page root = commonParaGenerator.getRoot();
                    hitGenerator.generate(gaDailyStats.getIp(),
                            gaDailyStats.getClientId(), gaDailyStats.getUserAgent(),
                            gaDailyStats.getGeoId(), root, "start");
                    List<Page> pages = commonParaGenerator.getPages(root);
                    long runtime = System.currentTimeMillis();
                    for (int i = 0; i < pages.size(); i ++) {
                        Page page = pages.get(i);
                        int gap = commonParaGenerator.getGap();
                        GaDailyProcess gaDailyProcess = new GaDailyProcess();
                        gaDailyProcess.setIp(gaDailyStats.getIp());
                        gaDailyProcess.setClientId(gaDailyStats.getClientId());
                        gaDailyProcess.setUserAgent(gaDailyStats.getUserAgent());
                        gaDailyProcess.setGeoId(gaDailyStats.getGeoId());
                        runtime = runtime + gap;
                        gaDailyProcess.setRunTime(runtime);
                        gaDailyProcess.setUrl(page.getUrl());
                        gaDailyProcess.setTitle(page.getTitle());
                        if (i + 1 == pages.size()) {
                            gaDailyProcess.setEnd(true);
                        } else {
                            gaDailyProcess.setEnd(false);
                        }
                        gaDailyProcessDao.createGaDailyProcess(gaDailyProcess);
                    }
                }
            } else {
                Thread.sleep(1000l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processScheduled() {
        try {
            List<GaDailyProcess> gaDailyProcesses = gaDailyProcessService.getGaDailyProcesses();
            for (GaDailyProcess gaDailyProcess : gaDailyProcesses) {
                Page page = new Page(gaDailyProcess.getUrl(), gaDailyProcess.getTitle()
                        , null, null);

                if (gaDailyProcess.getEnd() == true) {
                    hitGenerator.generate(gaDailyProcess.getIp(), gaDailyProcess.getClientId(),
                            gaDailyProcess.getUserAgent(), gaDailyProcess.getGeoId(), page, "end");
                } else {
                    hitGenerator.generate(gaDailyProcess.getIp(), gaDailyProcess.getClientId(),
                            gaDailyProcess.getUserAgent(), gaDailyProcess.getGeoId(), page, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @PostConstruct
//    public void process() {
//        for (int i = 0; i < threadNum; i ++) {
//            Future<?> f = taskExecutor.submit(new Process());
//        }
//    }
//
//
//    class Process implements Runnable {
//        public void run() {
//            System.out.println("Thread.currentThread() -> " + Thread.currentThread());
//            while (true) {
//                processDailyStats();
//            }
//        }
//    }

}
