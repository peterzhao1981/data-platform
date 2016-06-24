package com.mode.api;

import com.mode.service.UserService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kang on 2016/6/22.
 */
@RestController
@RequestMapping("/stats")
public class UserApi {

    @Autowired
    private UserService userService;

    static final SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");

    /**
     * Get daily data for user register,order ,gmv
     *
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<? extends Object> listStatsInfo(@RequestParam(value = "startDate") Integer startDate,
                                             @RequestParam(value = "endDate") Integer endDate,
                                             @RequestParam(value = "type") Integer type) {
        List<? extends Object> list = userService.listStatsInfo(startDate, endDate, type);
        return list;
    }

    /**
     * Get dashboard stats information
     *
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public List<? extends Object> listDashboardInfo() {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_MONTH, -1);
        Integer endDate = Integer.parseInt(sdf.format(rightNow.getTime()));
        rightNow.add(Calendar.DAY_OF_MONTH, -14);
        Integer startDate = Integer.parseInt(sdf.format(rightNow.getTime()));
        List<? extends Object> list = userService.listStatsInfo(startDate, endDate, 1);
        return list;
    }
}
