package com.mode.util;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
/**
 * Created by zhaoweiwei on 17/3/6.
 */
public class TimeZoneExample {

    public static void main(String[] args) {

//        String[] ids = TimeZone.getAvailableIDs();
//        for (String id : ids) {
//            System.out.println(id);
//            System.out.println(displayTimeZone(TimeZone.getTimeZone(id)));
//        }
//
//        System.out.println("\nTotal TimeZone ID " + ids.length);
        StringBuffer sb = new StringBuffer();
        sb.append("id").append("=").append(1);
        System.out.println(sb.toString());

    }

    private static String displayTimeZone(TimeZone tz) {

        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);

        String result = "";
        if (hours > 0) {
            result = String.format("(GMT+%d:%02d) %s", hours, minutes, tz.getID());
        } else {
            result = String.format("(GMT%d:%02d) %s", hours, minutes, tz.getID());
        }

        return result;

    }

}

