package com.mode.api;

import com.mode.entity.StatsDaily;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<StatsDaily> listUserRegister(@RequestParam(value = "startDate") Integer startDate,
                                             @RequestParam(value = "endDate") Integer endDate,
                                             @RequestParam(value = "type") Integer type) {
        List<StatsDaily> list = userService.listUserRegister(startDate, endDate, type);
        return list;
    }
}
