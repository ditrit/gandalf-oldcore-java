package com.orness.gandalf.core.library.gandalfjavaclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class GandalfJavaClient {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public GandalfJavaClient(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

}
