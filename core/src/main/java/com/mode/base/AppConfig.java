package com.mode.base;

/**
 * Pre-defined application-wise configurations for all the Java backend systems.
 *
 * @author chao
 */
public class AppConfig {

    public static final Integer LAST_PROCESSED_DAILY_DATE = 20160531;

    public static final Integer LAST_PROCESSED_WEEKLY_DATE = 20160529;

    public static final Integer LAST_PROCESSED_MONTHLY_DATE = 201605;

    // User come from mode
    public static final String USER_SOURCE_MODE = "mode";
    // User come from facebook
    public static final String USER_SOURCE_FACEBOOK = "facebook";
    // User come from youtube
    public static final String USER_SOURCE_YOUTUBE = "youtube";
    // User come from instagram
    public static final String USER_SOURCE_INSTAGRAM = "instagram";


    // Stats type: daily
    public static final String STATS_TYPE_DAILY = "daily";
    // Stats type: daily
    public static final String STATS_TYPE_WEEKLY = "weekly";
    // Stats type: daily
    public static final String STATS_TYPE_MONTHLY = "monthly";

}