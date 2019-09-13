package com.orness.gandalf.core.module.clustercore.worker;

import com.orness.gandalf.core.module.clustercore.properties.GandalfClusterProperties;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableCaptureWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.clustercore.constant.ClusterConstant.WORKER_SERVICE_CLASS_CAPTURE;

@Component
public class CaptureWorker extends RunnableCaptureWorkerZeroMQ {

    private GandalfClusterProperties gandalfClusterProperties;
    private RestTemplate restTemplate;

    @Autowired
    public CaptureWorker(GandalfClusterProperties gandalfClusterProperties) {
        this.gandalfClusterProperties = gandalfClusterProperties;
        this.restTemplate = new RestTemplate();
        this.initRunnable(WORKER_SERVICE_CLASS_CAPTURE, this.gandalfClusterProperties.getCommandCaptureConnection(), this.gandalfClusterProperties.getEventCaptureConnection());
    }

    @Override
    protected void executeRoutingWorkerCommand(ZMsg command) {
        //API
        //this.restTemplate...
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {
        //API
        //this.restTemplate...
    }
}
