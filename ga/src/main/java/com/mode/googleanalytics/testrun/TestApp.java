package com.mode.googleanalytics.testrun;

import java.util.ArrayList;
import java.util.List;

import com.mode.googleanalytics.AppViewHit;
import com.mode.googleanalytics.EventHit;
import com.mode.googleanalytics.GoogleAnalytics;

/**
 * Created by zhaoweiwei on 17/3/13.
 */
public class TestApp {

    public static void main(String[] args) {
        GoogleAnalytics ga = new GoogleAnalytics("UA-92870277-1");
        AppViewHit hit = new AppViewHit("Whatsmode", "1.0.0", "HOME");
//        hit.userIp("4.10." + 1 + "." + 9);
        hit.userIp("116.231.209.58");

        hit.clientId("0353b9b1-7ba3-4298-9fce-2cd35cca0324");
        hit.dataSource("app");
//        hit.applicationIdentifier("iPhone9,2");
//        hit.userAgent("GoogleAnalytics/3.17 (iPhone; U; CPU iOS 10.3 Beta 6 like Mac OS X)");
        hit.userAgent("GoogleAnalytics/3.17 (iPad; U; CPU iOS 11.5 like Mac OS X)");
//        hit.userAgent("GoogleAnalytics/3.17 (iPad Air; U; CPU iOS 10.2 like Mac OS X)");
        hit.applicationId("com.whatsmode.shop");

        hit.screenResolution("3300x3300");

        ga.post(hit);

//        List<String> list = new ArrayList<>();
//        list.add(null);
//        System.out.println(list.size());
//        if (list.get(0) == null) {
//            System.out.println(list.get(0));
//        }
    }

//    public static void main(String[] args) {
//        GoogleAnalytics ga = new GoogleAnalytics("UA-92870277-1");
//        EventHit hit = new EventHit("FACEBOOK", "SIGNUP", null, null);
//        hit.userIp("4.10." + 1 + "." + 9);
//        hit.clientId("0353b9b1-7ba3-4298-9fce-2cd35cca0320");
//        hit.dataSource("app");
//        hit.applicationIdentifier("iPhone9,2");
//        hit.applicationName("Whatsmode");
//        hit.applicationVersion("1.0.0");
//        hit.contentDescription("HOME");
////        hit.userAgent("GoogleAnalytics/3.17 (iPhone; U; CPU iOS 10.2 like Mac OS X; en-cn-cn)");
//        hit.userAgent("GoogleAnalytics/3.17 (iPad; U; CPU iOS 10.2 like Mac OS X)");
//        hit.applicationId("com.whatsmode.shop");
//
//        hit.sessionControl("start");
//
//        ga.post(hit);
//    }
}
