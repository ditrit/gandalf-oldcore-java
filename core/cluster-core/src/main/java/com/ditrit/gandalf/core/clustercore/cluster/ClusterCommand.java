package com.ditrit.gandalf.core.clustercore.cluster;

import com.ditrit.gandalf.core.clustercore.properties.ClusterProperties;
import com.ditrit.gandalf.core.clustercore.router.ClusterRouteur;
import com.ditrit.gandalf.core.zeromqcore.command.broker.BrokerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "clusterCommand")
@Scope("singleton")
public class ClusterCommand implements Runnable {

    private ClusterProperties clusterProperties;
    private ClusterRouteur clusterRouteur;

    @Autowired
    public ClusterCommand(ClusterProperties clusterProperties, ClusterRouteur clusterRouteur) {
        this.clusterProperties = clusterProperties;
        this.clusterRouteur = clusterRouteur;
    }

    @Override
    public void run() {
        new BrokerZeroMQ(clusterProperties.getCommandFrontEndConnection(), clusterProperties.getCommandBackEndConnection(), clusterProperties.getCommandCaptureConnection(), this.clusterRouteur);
    }
}