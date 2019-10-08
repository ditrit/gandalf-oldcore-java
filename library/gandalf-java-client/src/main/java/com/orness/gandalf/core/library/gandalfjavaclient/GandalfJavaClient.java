package com.orness.gandalf.core.library.gandalfjavaclient;

import com.ditrit.gandalf.core.clientcore.library.LibraryClient;
import com.ditrit.gandalf.core.listenercore.LibraryListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class GandalfJavaClient {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private LibraryClient libraryClient;
    private LibraryListener libraryListener;

    @Autowired
    public GandalfJavaClient(ThreadPoolTaskExecutor threadPoolTaskExecutor, LibraryClient libraryClient, LibraryListener libraryListener) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.libraryClient = libraryClient;
        this.libraryListener = libraryListener;
    }

    public LibraryClient getLibraryClient() {
        return libraryClient;
    }

    public LibraryListener getLibraryListener() {
        return libraryListener;
    }
}
