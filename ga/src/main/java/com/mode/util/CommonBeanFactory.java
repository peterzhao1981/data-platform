package com.mode.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
@Configuration
public class CommonBeanFactory {

    @Value("${thread.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${thread.corePoolSize}")
    private Integer corePoolSize;

    @Value("${thread.queueCapacity}")
    private Integer queueCapacity;

    @Value("${thread.keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskExecutor;
    }

    @Bean
    public DateFormat dateFormat() {
        return new SimpleDateFormat("yyyyMMdd");
    }
}
