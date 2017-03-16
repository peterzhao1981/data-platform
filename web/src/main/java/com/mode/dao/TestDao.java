package com.mode.dao;

import org.apache.ibatis.annotations.Insert;

/**
 * Created by zhaoweiwei on 16/9/22.
 */
public interface TestDao {

    @Insert("INSERT INTO md_test (a) values (#{a})")
    public Integer createTest(int a);
}
