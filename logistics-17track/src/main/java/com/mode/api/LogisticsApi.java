package com.mode.api;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mode.service.LogisticsService;

/**
 * Created by zhaoweiwei on 16/12/28.
 */
@RestController
public class LogisticsApi {

    @Autowired
    private LogisticsService logisticsService;

    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    public void getLogistics() {
        System.out.println("start");
        logisticsService.updateLogistics();
    }

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(4000 + random.nextInt(2001));
    }
}
