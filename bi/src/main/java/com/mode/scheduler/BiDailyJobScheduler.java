package com.mode.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mode.service.BiDailyJobProcessingService;


/**
 * Created by zhaoweiwei on 16/6/21.
 */
@Component
public class BiDailyJobScheduler {

    // Define the logger object for this class
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());


    @Autowired
    BiDailyJobProcessingService biDailyJobProcessingService;

    @Scheduled(cron = "0 0 14 * * ?")
    public void biDailyJobScheduler() {
        LOG.info("BI daily scheduler job start");
        //biDailyJobProcessingService.process();
        LOG.info("BI daily scheduler job end");
    }
}
