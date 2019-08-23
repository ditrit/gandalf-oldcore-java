package com.orness.gandalf.core.test.testzeromq.gandalf;

import com.google.gson.Gson;
import com.orness.gandalf.core.test.testzeromq.command.RunnableRoutingWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_COMMAND_SERVICE_GANDALF;

@Component
public class GandalfRoutingWorker extends RunnableRoutingWorker {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfRoutingWorker(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.mapper = new Gson();
        String[] connections = new String[3];
        connections[0] = gandalfProperties.getWorkerFrontEndConnection1();
        connections[1] = gandalfProperties.getWorkerFrontEndConnection2();
        connections[2] = gandalfProperties.getWorkerFrontEndConnection3();
        //TODO IDENTITY
        this.init(WORKER_COMMAND_SERVICE_GANDALF, connections, gandalfProperties.getWorkerBackEndConnection());
    }
}