package com.orness.gandalf.core.library.gandalfjavaclient.core;

import com.orness.gandalf.core.library.gandalfjavaclient.properties.GandalfClientProperties;
import com.orness.gandalf.core.module.zeromqcore.command.client.RunnableClientZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfClientZeroMQ extends RunnableClientZeroMQ {

    private GandalfClientProperties gandalfClientProperties;

    @Autowired
    public GandalfClientZeroMQ(GandalfClientProperties gandalfClientProperties) {
        super();
        this.gandalfClientProperties = gandalfClientProperties;
        this.initRunnable(this.gandalfClientProperties.getConnectorName(), this.gandalfClientProperties.getClientCommandBackEndConnections());
    }
}
