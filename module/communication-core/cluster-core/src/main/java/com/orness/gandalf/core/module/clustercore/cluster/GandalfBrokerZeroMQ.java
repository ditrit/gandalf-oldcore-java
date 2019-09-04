package com.orness.gandalf.core.module.clustercore.cluster;

import com.orness.gandalf.core.module.clustercore.properties.GandalfClusterProperties;
import com.orness.gandalf.core.module.zeromqcore.command.broker.BrokerZeroMQ;
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