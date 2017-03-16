package com.mode.googleanalytics.testrun;

import java.util.Random;
import java.util.UUID;

import com.mode.googleanalytics.DefaultRequest;
import com.mode.googleanalytics.GoogleAnalytics;
import com.mode.googleanalytics.GoogleAnalyticsConfig;
import com.mode.googleanalytics.GoogleAnalyticsRequest;
import com.mode.googleanalytics.PageViewHit;

/**
 * Created by zhaoweiwei on 17/1/16.
 */
public class Test {

    private static String[] twCities = {"9040379", "9040379", "9040379", "9040379", "9040379",
            "9040379", "9040379", "9040379", "9040379", "9040379", "9040379", "9040379",
            "9040379", "9040379", "9040379", "9040379",
            "1012825", "1012825", "1012825", "1012825",
            "9040314", "9040314", "9040314",
            "9040380", "9040380",
            "1012826", "1012818", "2158"};

    private static String clientId = UUID.randomUUID().toString();

    private static Random random = new Random();

    public static void main(String[] args) {

        String ip = "58.86.";

//        System.out.println(Math.round(2147483647 * Math.random()));
//
//        System.out.println(Math.round(Math.random() * 0x7FFFFFFF) + "." + Math.round(System
//                .currentTimeMillis() /
//                1000));

        for (int i = 0; i <= 0; i ++) {
            GoogleAnalytics ga = new GoogleAnalytics("UA-85898657-1");
//            PageViewHit hit = new PageViewHit("http://www.whatsmode.com/test", "Whatsmode View");
//            hit.userIp(ip + 1 + "." + 0);
//            //String clientId = UUID.randomUUID().toString();
//            hit.clientId("0353b9b1-7ba3-4298-9fce-2cd35cca0312");
//            hit.sessionControl("start");
//            ga.post(hit);
//
//            try {
//                Thread.sleep(20000l);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            hit = new PageViewHit("http://www.whatsmode.com/new-arrivals.html?mode=grid", "Whatsmode View");

//            GoogleAnalyticsRequest googleAnalyticsRequest = new GoogleAnalyticsRequest();
//            googleAnalyticsRequest.userIp(ip + 1 + "." + 0);
//            //System.out.println(hit.clientId());
//            //hit.clientId(clientId);
//            googleAnalyticsRequest.clientId("0353b9b1-7ba3-4298-9fce-2cd35cca0312");
//            googleAnalyticsRequest.sessionControl("end");
//            ga.post(googleAnalyticsRequest);


            PageViewHit hit = new PageViewHit("http://www.whatsmode.com/end", "Whatsmode View");
            hit.userIp(ip + 1 + "." + 0);
            hit.clientId("0353b9b1-7ba3-4298-9fce-2cd35cca0312");
            hit.sessionControl("end");
            ga.post(hit);
        }


//        for (int i = 0; i <= 255; i ++) {
//            for (int j = 0; j <= 255; j ++) {
//                //GoogleAnalytics ga = new GoogleAnalytics("UA-89086227-1");
//                GoogleAnalytics ga = new GoogleAnalytics("UA-85898657-1");
//                PageViewHit hit = new PageViewHit("http://www.whatsmode.com/test", "Whatsmode " +
//                        "View");
//                 hit.userIp(ip + i + "." + j);
//                //hit.userIp(ip + i + "." + 0);
//
//                hit.geoId(twCities[random.nextInt(twCities.length)]);
//                hit.userLanguage("zh-tw");
//                String clientId = Math.round(Math.random() * 0x7FFFFFFF) + "." + Math.round(System
//                .currentTimeMillis() /
//                1000);
//                hit.clientId(clientId);
//                ga.post(hit);
//
//            }
//        }

//        for (int i = 120; i <= 120; i ++) {
//            GoogleAnalytics ga = new GoogleAnalytics("UA-89086227-1");
//            PageViewHit hit = new PageViewHit("http://www.whatsmode.com", "Whatsmode View");
//            hit.userIp(ip + i);
//            String clientId = UUID.randomUUID().toString();
//            hit.clientId(clientId);
//            hit.sessionControl("start");
//            ga.post(hit);
//
//            try {
//                Thread.sleep(10000l);
//            } catch (InterruptedException e) {
//
//            }
//
//            hit = new PageViewHit("http://www.whatsmode.com/new-arrivals.html?mode=grid", "Whatsmode View");
//            hit.userIp(ip + i);
//            hit.clientId(clientId);
//            hit.sessionControl("end");
//            ga.post(hit);
//
//        }


//        DefaultRequest defaultRequest = new DefaultRequest(null, "UA-89086227-1", null, null);
//
//        GoogleAnalyticsConfig googleAnalyticsConfig = new GoogleAnalyticsConfig();
//        googleAnalyticsConfig.setProxyHost("210.101.131.231");
//        googleAnalyticsConfig.setProxyPort(8080);
//
//        GoogleAnalytics ga = new GoogleAnalytics(googleAnalyticsConfig, defaultRequest);
//        ga.post(new PageViewHit("http://www.whatsmode.com", "Whatsmode View"));
    }
}
