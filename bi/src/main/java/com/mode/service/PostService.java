package com.mode.service;

import com.mode.entity.Post;
import java.util.List;

/**
 * Created by kang on 2016/6/29.
 */
public interface PostService {

    public List<Post> getStatsPost(Long startDate, Long endDate);
}
