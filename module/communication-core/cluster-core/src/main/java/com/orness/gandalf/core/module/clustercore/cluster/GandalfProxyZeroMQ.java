package com.orness.gandalf.core.module.clustercore.cluster;

import com.orness.gandalf.core.module.clustercore.properties.GandalfClusterProperties;
import com.orness.gandalf.core.module.zeromqcore.event.proxy.ProxyZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "gandalfProxy")
@Scope("singleton")
public class GandalfProxyZeroMQ implements Runnable {

    private GandalfClusterProperties gandalfClusterProperties;

    @Autowired
    public GandalfProxyZeroMQ(GandalfClusterProperties gandalfClusterProperties) {
        this.gandalfClusterProperties = gandalfClusterProperties;
    }

    @Override
    public void run() {
        new ProxyZeroMQ(gandalfClusterProperties.getProxyFrontEndConnection(), gandalfClusterProperties.getProxyBackEndConnection(), gandalfClusterProperties.getProxyCaptureConnection());
    }
}