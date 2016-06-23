package com.mode.api;

import com.mode.entity.StatsDaily;
import com.mode.entity.StatsMonthly;
import com.mode.entity.StatsWeekly;
import com.mode.service.UserService;
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
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserService userService;

    /**
     * Get daily data for user register,order ,gmv
     *
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<StatsDaily> listUserRegister(@RequestParam(value = "startDate") Integer startDate,
                                             @RequestParam(value = "endDate") Integer endDate,
                                             @RequestParam(value = "type") Integer type) {
        List<StatsDaily> list = userService.listUserRegister(startDate, endDate, type);
        return list;
    }

    /**
     * Get weekly active user
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/weekly", method = RequestMethod.GET)
    public List<StatsWeekly> listWeeklyActivityUser(@RequestParam(value = "startDate") Integer
                                                             startDate,
                                                     @RequestParam(value = "endDate") Integer
                                                             endDate) {
        List<StatsWeekly> list = userService.listWeeklyActivityUser(startDate, endDate);
        return list;
    }

    /**
     * Get monthly active user
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/monthly", method = RequestMethod.GET)
    public List<StatsMonthly> listMonthlyActivityUser(@RequestParam(value = "startDate") Integer startDate,
                                                    @RequestParam(value = "endDate") Integer endDate) {
        List<StatsMonthly> list = userService.listMonthlyActivityUser(startDate, endDate);
        return list;
    }
}
