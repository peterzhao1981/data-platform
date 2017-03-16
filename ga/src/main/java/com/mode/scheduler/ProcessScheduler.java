package com.mode.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mode.service.GaAppGenerateService;

/**
 * Created by zhaoweiwei on 17/1/23.
 */
@Component
public class ProcessScheduler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GaAppGenerateService gaAppGenerateService;

//    @Scheduled(fixedRate = 10 * 1000)
//    public void processScheduler() {
//        LOG.info("Process scheduler job start");
//        gaWebGenerateService.processScheduled();
//        LOG.info("Process scheduler job end");
//    }

    @Scheduled(fixedRate = 10 * 1000)
    public void processScheduler() {
        LOG.info("Process scheduler job start");
        gaAppGenerateService.processScheduled();
        LOG.info("Process scheduler job end");
    }
}
