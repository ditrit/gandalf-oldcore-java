package com.ditrit.gandalf.core.clustercore.worker;

import com.ditrit.gandalf.core.clustercore.constant.ClusterConstant;
import com.ditrit.gandalf.core.clustercore.properties.GandalfClusterProperties;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableCaptureWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

@Component
public class CaptureWorker extends RunnableCaptureWorkerZeroMQ {

    private GandalfClusterProperties gandalfClusterProperties;
    private RestTemplate restTemplate;

    @Autowired
    public CaptureWorker(GandalfClusterProperties gandalfClusterProperties) {
        this.gandalfClusterProperties = gandalfClusterProperties;
        this.restTemplate = new RestTemplate();
        this.initRunnable(ClusterConstant.WORKER_SERVICE_CLASS_CAPTURE, this.gandalfClusterProperties.getCommandCaptureConnection(), this.gandalfClusterProperties.getEventCaptureConnection());
    }

    @Override
    protected void executeRoutingWorkerCommand(ZMsg command) {
        //API
        System.out.println("COMMAND");
        System.out.println(command);
        //this.restTemplate...
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {
        //API
        System.out.println("EVENT");
        System.out.println(command);
        //this.restTemplate...
    }
}
