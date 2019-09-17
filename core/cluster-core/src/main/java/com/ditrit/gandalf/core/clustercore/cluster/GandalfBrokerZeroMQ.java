package com.ditrit.gandalf.core.clustercore.cluster;

import com.ditrit.gandalf.core.clustercore.properties.GandalfClusterProperties;
import com.ditrit.gandalf.core.zeromqcore.command.broker.BrokerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "gandalfBroker")
@Scope("singleton")
public class GandalfBrokerZeroMQ implements Runnable {

    private GandalfClusterProperties gandalfClusterProperties;

    @Autowired
    public GandalfBrokerZeroMQ(GandalfClusterProperties gandalfClusterProperties) {
        this.gandalfClusterProperties = gandalfClusterProperties;
    }

    @Override
    public void run() {
        new BrokerZeroMQ(gandalfClusterProperties.getCommandFrontEndConnection(), gandalfClusterProperties.getCommandBackEndConnection(), gandalfClusterProperties.getCommandCaptureConnection());
    }
}