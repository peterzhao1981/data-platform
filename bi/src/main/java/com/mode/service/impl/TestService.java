package com.mode.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mode.dao.ElasticSearchDao;
import com.mode.dao.target.StatsCountryDao;
import com.mode.entity.StatsCountry;

/**
 * Created by zhaoweiwei on 16/6/23.
 */
@Service
public class TestService {

    @Autowired
    private StatsCountryDao statsCountryDao;

    @Autowired
    private ElasticSearchDao elasticSearchDao;



    public void test() throws Exception {
        long start = 1464537600000l;
        long end = 1464624000000l;


        List<Long> list = elasticSearchDao.getRequsetCountByHour(start, end);

        for (Long l : list) {
            System.out.println(l);
        }

    }

}
