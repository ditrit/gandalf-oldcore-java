package com.orness.gandalf.core.test.testworker.core;

import com.google.gson.Gson;
import com.orness.gandalf.core.test.testworker.properties.GandalfProperties;
import com.orness.gandalf.core.test.testzeromq.command.RunnableRoutingWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfRoutingWorker extends RunnableRoutingWorker {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfRoutingWorker(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.mapper = new Gson();
        String[] connections = new String[3];
        connections[0] = this.gandalfProperties.getRoutingWorkerFrontEndConnection1();
        connections[1] = this.gandalfProperties.getRoutingWorkerFrontEndConnection2();
        connections[2] = this.gandalfProperties.getRoutingWorkerFrontEndConnection3();
        System.out.println(connections);
        this.initRunnable(this.gandalfProperties.getConnectorName(), connections, this.gandalfProperties.getRoutingWorkerBackEndConnection());
    }
}