package com.mode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mode.dao.GaGeoConfigDao;
import com.mode.dao.GaUserAgentConfigDao;
import com.mode.entity.Page;
import com.mode.googleanalytics.AppViewHit;
import com.mode.googleanalytics.GoogleAnalytics;

/**
 * Created by zhaoweiwei on 17/3/15.
 */
@Component
public class HitAppGenerator {

    @Value("${application.app.reference.code}")
    private String referenceCode;

    private Random random = new Random();

    private String appId = "com.10buck.mode";

    private String appName = "Whatsmode";

    private String[] appVersions = {"4.3.3", "4.3.3", "4.3.3", "4.3.3", "4.3.3", "4.3.3",
            "4.3.3", "4.3.3", "4.3.3", "4.3.3", "4.3.2"};

    private String dataSource = "app";

    private String[] iphoneScreenResolutions = {"640x1136", "750x1334", "750x1334",
            "750x1334", "750x1334", "750x1334", "750x1334", "750x1334", "750x1334",
            "750x1334", "750x1334", "1242x2208", "1242x2208", "1242x2208", "1242x2208",
            "1242x2208", "1242x2208", "1242x2208", "1242x2208"};

    private String[] ipadScreenResolutions = {"2048x2732", "1536x2048", "1536x2048", "1536x2048",
            "1536x2048", "1536x2048", "1536x2048", "1536x2048", "1536x2048", "1536x2048"};


    @Autowired
    private GaGeoConfigDao gaGeoConfigDao;

    @Autowired
    private GaUserAgentConfigDao gaUserAgentConfigDao;

    public void generate(String ip, String clientId, String userAgent, String geoId,
                         String content, String sessionCtrl) {
        GoogleAnalytics ga = new GoogleAnalytics(referenceCode);
        AppViewHit hit = new AppViewHit(appName, appVersions[random.nextInt(appVersions.length)],
                content);
        hit.applicationId(appId);
        hit.userIp(ip);
        hit.clientId(clientId);
        hit.dataSource(dataSource);
        hit.userAgent(userAgent);
        if (userAgent.toLowerCase().indexOf("ipad") != -1) {
            hit.screenResolution(ipadScreenResolutions[random.nextInt(ipadScreenResolutions.length)]);
        } else {
            hit.screenResolution(iphoneScreenResolutions[random.nextInt(iphoneScreenResolutions.length)]);
        }

        // Set geoId
        if (StringUtils.isEmpty(geoId) || "notSet".equals(geoId)) {
            hit.geoId(null);
        } else {
            hit.geoId(geoId);
        }

        hit.userLanguage("en-us");
        if (!StringUtils.isEmpty(sessionCtrl)) {
            hit.sessionControl(sessionCtrl);
        }
        ga.post(hit);
    }


    public static void main(String[] args) {
//        HitAppGenerator h = new HitAppGenerator();
//        System.out.println(h.getIosVersion());
        String s = "iPhone";
        System.out.println(s.toLowerCase().indexOf("iphone"));

    }


}
