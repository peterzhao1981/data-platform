package com.mode.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mode.entity.Page;
import com.mode.googleanalytics.GoogleAnalytics;
import com.mode.googleanalytics.PageViewHit;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
@Component
public class HitGenerator {

    @Value("${application.web.reference.code}")
    private String referenceCode;


    private Random random = new Random();

    public void generate(String ip, String clientId, String userAgent, String geoId, Page page,
                         String sessionCtrl) {
        GoogleAnalytics ga = new GoogleAnalytics(referenceCode);
        PageViewHit hit = new PageViewHit(page.getUrl(), page.getTitle());
        hit.userIp(ip);
        hit.clientId(clientId);
        // Set user agent
        hit.userAgent(userAgent);
        // Set geoId
        if (StringUtils.isEmpty(geoId) || "notSet".equals(geoId)) {
            hit.geoId(null);
        } else {
            hit.geoId(geoId);
        }

//        hit.geoId(twCities[random.nextInt(twCities.length)]);
//        hit.userLanguage("zh-tw");

        hit.userLanguage("en-us");
        if (!StringUtils.isEmpty(sessionCtrl)) {
            hit.sessionControl(sessionCtrl);
        }
        ga.post(hit);
    }
}
