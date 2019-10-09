package com.ditrit.gandalf.library.gandalfworkerclient;

import com.ditrit.gandalf.core.clientcore.worker.WorkerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryWorkerClient {

    private WorkerClient workerClient;

    @Autowired
    public LibraryWorkerClient(WorkerClient workerClient) {
        this.workerClient = workerClient;
    }

    public WorkerClient getWorkerClient() {
        return workerClient;
    }
}
