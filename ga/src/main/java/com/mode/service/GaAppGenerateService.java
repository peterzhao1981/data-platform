package com.mode.service;

import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.mode.dao.GaDailyProcessDao;
import com.mode.entity.GaDailyProcess;
import com.mode.entity.GaDailyStats;
import com.mode.util.CommonParaGenerator;
import com.mode.util.HitAppGenerator;

/**
 * Created by zhaoweiwei on 17/3/15.
 */
@Service
public class GaAppGenerateService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private GaAppDailyStatsService gaAppDailyStatsService;

    @Autowired
    private GaDailyProcessService gaDailyProcessService;

    @Autowired
    private GaDailyProcessDao gaDailyProcessDao;

    @Autowired
    private CommonParaGenerator commonParaGenerator;

    @Autowired
    private HitAppGenerator hitAppGenerator;

    @Value("${application.thread.num}")
    private Integer threadNum;

    public void processDailyStats() {
        try {
            GaDailyStats gaDailyStats = gaAppDailyStatsService.getGaDailyStats();
            if (gaDailyStats != null) {
                String[] content = commonParaGenerator.getAppContent();
                if (content == null) {
                    hitAppGenerator.generate(gaDailyStats.getIp(), gaDailyStats.getClientId(),
                            gaDailyStats.getUserAgent(), gaDailyStats.getGeoId(), "HOME", null);
                } else {
                    hitAppGenerator.generate(gaDailyStats.getIp(), gaDailyStats.getClientId(),
                            gaDailyStats.getUserAgent(), gaDailyStats.getGeoId(), "HOME", "start");
                    long runtime = System.currentTimeMillis();
                    for (int i = 0; i < content.length; i ++) {
                        int gap = commonParaGenerator.getGap();
                        GaDailyProcess gaDailyProcess = new GaDailyProcess();
                        gaDailyProcess.setIp(gaDailyStats.getIp());
                        gaDailyProcess.setClientId(gaDailyStats.getClientId());
                        gaDailyProcess.setUserAgent(gaDailyStats.getUserAgent());
                        gaDailyProcess.setGeoId(gaDailyStats.getGeoId());
                        runtime = runtime + gap;
                        gaDailyProcess.setRunTime(runtime);
                        gaDailyProcess.setUrl(content[i]);
                        if (i + 1 == content.length) {
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

                if (gaDailyProcess.getEnd() == true) {
                    hitAppGenerator.generate(gaDailyProcess.getIp(), gaDailyProcess.getClientId(),
                            gaDailyProcess.getUserAgent(), gaDailyProcess.getGeoId(),
                            gaDailyProcess.getUrl(), "end");
                } else {
                    hitAppGenerator.generate(gaDailyProcess.getIp(), gaDailyProcess.getClientId(),
                            gaDailyProcess.getUserAgent(), gaDailyProcess.getGeoId(),
                            gaDailyProcess.getUrl(), null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void process() {
        for (int i = 0; i < threadNum; i ++) {
            Future<?> f = taskExecutor.submit(new Process());
        }
    }


    class Process implements Runnable {
        public void run() {
            System.out.println("Thread.currentThread() -> " + Thread.currentThread());
            while (true) {
                processDailyStats();
            }
        }
    }
}
