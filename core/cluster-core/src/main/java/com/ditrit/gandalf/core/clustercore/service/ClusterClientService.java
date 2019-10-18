package com.ditrit.gandalf.core.clustercore.service;

import com.ditrit.gandalf.core.clustercore.properties.ClusterProperties;
import com.ditrit.gandalf.core.zeromqcore.service.client.RunnableClientServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "clusterClientService")
@Scope("singleton")
public class ClusterClientService extends RunnableClientServiceZeroMQ {

    private ClusterProperties clusterProperties;

    @Autowired
    public ClusterClientService(ClusterProperties clusterProperties) {
        super();
        this.clusterProperties = clusterProperties;
        this.initRunnable(this.clusterProperties.getName(), this.clusterProperties.getServiceClientConnection());
    }

    @Override
    public ZMsg sendRequest(Object request) {
        return null;
    }
}
