package com.ditrit.gandalf.core.clustercore.cluster;

import com.ditrit.gandalf.core.clustercore.properties.ClusterProperties;
import com.ditrit.gandalf.core.zeromqcore.event.proxy.ProxyZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "clusterEvent")
@Scope("singleton")
public class ClusterEvent implements Runnable {

    private ClusterProperties clusterProperties;

    @Autowired
    public ClusterEvent(ClusterProperties clusterProperties) {
        this.clusterProperties = clusterProperties;
    }

    @Override
    public void run() {
        new ProxyZeroMQ(clusterProperties.getEventFrontEndConnection(), clusterProperties.getEventBackEndConnection(), clusterProperties.getEventCaptureConnection());
    }
}