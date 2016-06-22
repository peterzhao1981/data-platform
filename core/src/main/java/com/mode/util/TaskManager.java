package com.mode.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * To give other Spring components an abstraction for thread pooling where needed.
 * Created by chao on 7/16/15.
 */
public class TaskManager {

    private static ThreadPoolTaskExecutor taskExecutor;

    public TaskManager(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }
}