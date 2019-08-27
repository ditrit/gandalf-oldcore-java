package com.orness.gandalf.core.test.testclient.core;

import com.orness.gandalf.core.test.testclient.properties.GandalfProperties;
import com.orness.gandalf.core.test.testzeromq.command.Client;
import com.orness.gandalf.core.test.testzeromq.command.RunnableClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfClient extends RunnableClient {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfClient(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        String[] connections = new String[3];
        connections[0] = this.gandalfProperties.getClientBackEndConnection1();
        connections[1] = this.gandalfProperties.getClientBackEndConnection2();
        connections[2] = this.gandalfProperties.getClientBackEndConnection3();
        System.out.println(this.gandalfProperties.getConnectorName());
        this.initRunnable(this.gandalfProperties.getConnectorName(), connections);
    }



}
