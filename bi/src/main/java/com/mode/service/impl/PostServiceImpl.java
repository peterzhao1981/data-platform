package com.mode.service.impl;

import com.mode.base.Message;
import com.mode.dao.source.PostDao;
import com.mode.dao.source.UserActionLogDao;
import com.mode.entity.Post;
import com.mode.exception.ModeException;
import com.mode.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kang on 2016/6/29.
 */
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserActionLogDao userActionLogDao;

    public List<Post> getStatsPost(Long startDate, Long endDate) {
        List<Post> list = postDao.getStatsPost(startDate, endDate);
        if (list == null || list.isEmpty()) {
            throw new ModeException(Message.NO_MORE_DATA);
        }
        for (Post tmp : list) {
            Integer postId = tmp.getId();
            tmp.setViews(userActionLogDao.countPostViews(postId));
        }
        return list;
    }
}
