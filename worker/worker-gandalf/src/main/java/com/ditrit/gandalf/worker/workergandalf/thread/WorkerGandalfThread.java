package com.ditrit.gandalf.worker.workergandalf.thread;

import com.ditrit.gandalf.worker.workergandalf.configuration.WorkerGandalfThreadConfiguration;
import com.ditrit.gandalf.worker.workergandalf.functions.WorkerGandalfFunctions;
import function.WorkerFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import worker.Worker;

public class WorkerGandalfThread {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Worker worker;
    @Autowired
    private WorkerGandalfThreadConfiguration workerGandalfThreadConfiguration;
    @Autowired
    private WorkerFunctions workerFunctions;
    @Autowired
    private WorkerGandalfFunctions workerGandalfFunctions;

    private Thread currentThread;

    public WorkerGandalfThread() {
    }

    public void start() {
        this.workerFunctions.setCommands(this.workerGandalfFunctions.getCommands());
        this.workerFunctions.setEvents(this.workerGandalfFunctions.getEvents());
        this.currentThread = new Thread(this.worker);
        this.currentThread.start();
    }
}
