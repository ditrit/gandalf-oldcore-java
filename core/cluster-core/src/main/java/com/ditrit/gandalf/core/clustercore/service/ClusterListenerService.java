package com.ditrit.gandalf.core.clustercore.service;

import com.ditrit.gandalf.core.clustercore.properties.ClusterProperties;
import com.ditrit.gandalf.core.zeromqcore.service.listener.RunnableListenerServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "clusterListenerService")
@Scope("singleton")
public class ClusterListenerService extends RunnableListenerServiceZeroMQ {

    private ClusterProperties clusterProperties;

    @Autowired
    public ClusterListenerService(ClusterProperties clusterProperties) {
        super();
        this.clusterProperties = clusterProperties;
        this.initRunnable(this.clusterProperties.getName(), this.clusterProperties.getServiceListenerConnection());
    }

    @Override
    public String processRequestService(ZMsg request) {
        return null;
    }
}
