package com.mode.service;

import com.mode.entity.StatsDaily;
import java.util.List;

/**
 * Created by kang on 2016/6/22.
 */
public interface UserService {

    public List<StatsDaily> listUserRegister(Integer startDate, Integer endDate, Integer type);
}
