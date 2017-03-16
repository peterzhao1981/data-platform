package com.mode.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mode.dao.GaDailyProcessDao;
import com.mode.dao.GaDailyStatsDao;
import com.mode.service.GaDailyStatsService;
import com.mode.service.GaWebGenerateService;
import com.mode.util.CommonParaGenerator;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
@RestController
public class TestApi {

    @Autowired
    private GaDailyStatsService gaDailyStatsService;

    @Autowired
    private GaWebGenerateService gaWebGenerateService;

    @Autowired
    private GaDailyStatsDao gaDailyStatsDao;

    @Autowired
    private GaDailyProcessDao gaDailyProcessDao;

    @Autowired
    private CommonParaGenerator commonParaGenerator;



    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test() {
        //gaWebGenerateService.process();
        List<String> ips = gaDailyStatsDao.getIps();
        for (String ip : ips) {
            String userAgent = commonParaGenerator.getUserAgent();
            System.out.println(userAgent);
            String geoId = commonParaGenerator.getGeoId();
            System.out.println(geoId);
            gaDailyStatsDao.updateUserAgentAndGeoId(ip, userAgent, geoId);
            gaDailyProcessDao.updateUserAgentAndGeoId(ip, userAgent, geoId);
        }
    }


}
