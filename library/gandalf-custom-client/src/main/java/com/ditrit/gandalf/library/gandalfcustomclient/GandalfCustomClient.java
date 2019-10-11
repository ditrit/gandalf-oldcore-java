package com.ditrit.gandalf.library.gandalfcustomclient;

import com.ditrit.gandalf.core.clientcore.custom.CustomClient;
import com.ditrit.gandalf.core.clientcore.library.LibraryClient;
import com.ditrit.gandalf.core.listenercore.LibraryListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class GandalfCustomClient {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private CustomClient customClient;

    @Autowired
    public GandalfCustomClient(ThreadPoolTaskExecutor threadPoolTaskExecutor, CustomClient customClient) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.customClient = customClient;
    }

    public CustomClient getCustomClient() {
        return customClient;
    }

}

