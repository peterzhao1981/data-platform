package com.mode.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mode.dao.TestDao;
import com.mode.dao2.CalendarDao;

/**
 * Created by zhaoweiwei on 16/9/21.
 */
@RestController
public class TestApi {

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private TestDao testDao;

//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public Integer test() throws Exception {
//        return userDao.getNewUsersByAuthority(1, 0l, System.currentTimeMillis());
//    }


//    @Autowired
//    @Qualifier("jdbcPrimaryTemplate")
//    private JdbcTemplate jdbcPrinaryTemplate;
//
//    @Autowired
//    @Qualifier("jdbcSecondaryTemplate")
//    private JdbcTemplate jdbcSecondaryTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @Transactional(transactionManager = "targetTm")
    public Integer test() throws Exception {
        int i1 = testDao.createTest(2);
        int j1 = calendarDao.createTest(2);
//        int i = 1;
//        int j = 0;
//        i = i / j;
//        return 1;

        return i1;
//        Integer primaryCount = jdbcPrinaryTemplate.queryForObject("SELECT count(*) FROM " +
//                "md_test",
//                Integer.class);

//        jdbcPrinaryTemplate.execute("insert into md_test values(2)");
//
//        Integer secondaryCount = jdbcSecondaryTemplate.queryForObject("SELECT count(*) FROM md_calendar",
//                Integer.class);
//
//        jdbcSecondaryTemplate.execute("insert into md_test(`key`,`value`) values('2', '2')");
//
//        System.out.println(secondaryCount);
//
//        int i = 1;
//        int j = 0;
//        i = i / j;
//
//        return secondaryCount;

    }
}
