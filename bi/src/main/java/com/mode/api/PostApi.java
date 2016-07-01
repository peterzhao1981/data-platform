package com.mode.api;

import com.mode.entity.Post;
import com.mode.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kang on 2016/6/29.
 */
@RestController
@RequestMapping("/posts")
public class PostApi {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Post> getStatsPost(@RequestParam(value = "startDate") Long startDate,
                                   @RequestParam(value = "endDate") Long endDate) {
        List<Post> list = postService.getStatsPost(startDate, endDate);
        return list;
    }
}
