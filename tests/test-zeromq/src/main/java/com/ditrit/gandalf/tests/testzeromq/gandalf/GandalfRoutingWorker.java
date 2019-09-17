package com.ditrit.gandalf.tests.testzeromq.gandalf;

import com.ditrit.gandalf.tests.testzeromq.command.RunnableRoutingWorker;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ditrit.gandalf.tests.testzeromq.Constant.WORKER_SERVICE_CLASS_ADMIN;

@Component
public class GandalfRoutingWorker extends RunnableRoutingWorker {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfRoutingWorker(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.mapper = new Gson();
        String[] connections = new String[3];
        connections[0] = gandalfProperties.getRoutingWorkerFrontEndConnection1();
        connections[1] = gandalfProperties.getRoutingWorkerFrontEndConnection2();
        connections[2] = gandalfProperties.getRoutingWorkerFrontEndConnection3();
        this.initRunnable(WORKER_SERVICE_CLASS_ADMIN, connections, this.gandalfProperties.getRoutingWorkerBackEndConnection());
    }
}