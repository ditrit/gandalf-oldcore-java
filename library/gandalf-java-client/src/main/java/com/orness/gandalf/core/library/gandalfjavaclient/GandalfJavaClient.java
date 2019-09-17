package com.orness.gandalf.core.library.gandalfjavaclient;

import com.ditrit.gandalf.core.clientcore.GandalfClient;
import com.ditrit.gandalf.core.listenercore.GandalfListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class GandalfJavaClient {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private GandalfClient gandalfClient;
    private GandalfListener gandalfListener;

    @Autowired
    public GandalfJavaClient(ThreadPoolTaskExecutor threadPoolTaskExecutor, GandalfClient gandalfClient, GandalfListener gandalfListener) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.gandalfClient = gandalfClient;
        this.gandalfListener = gandalfListener;
    }

}
