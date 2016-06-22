package com.mode.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mode.service.BiDailyProcessingService;

/**
 * Created by zhaoweiwei on 16/6/20.
 */
@RestController
public class TestApi {

    @Autowired
    private BiDailyProcessingService biDailyProcessingService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        biDailyProcessingService.process();
    }
}
