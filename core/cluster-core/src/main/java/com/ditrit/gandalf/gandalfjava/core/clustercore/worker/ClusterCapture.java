package com.ditrit.gandalf.gandalfjava.core.clustercore.worker;

import com.ditrit.gandalf.gandalfjava.core.clustercore.constant.ClusterConstant;
import com.ditrit.gandalf.gandalfjava.core.clustercore.properties.ClusterProperties;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.RunnableCaptureWorkerZeroMQ;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

import java.util.Arrays;

@Component(value = "clusterCapture")
@Scope("singleton")
public class ClusterCapture extends RunnableCaptureWorkerZeroMQ {

    private ClusterProperties clusterProperties;
    private RestTemplate restTemplate;

    @Autowired
    public ClusterCapture(ClusterProperties clusterProperties) {
        this.clusterProperties = clusterProperties;
        this.restTemplate = new RestTemplate();
        this.initRunnable(ClusterConstant.WORKER_SERVICE_CLASS_CAPTURE, this.clusterProperties.getCommandCaptureConnection(), this.clusterProperties.getEventCaptureConnection());
    }

    @Override
    protected void executeRoutingWorkerCommand(ZMsg command) {
        //API
        System.out.println("COMMAND");
        System.out.println(command);
        JsonArray commandJson = new JsonArray();
        Arrays.stream(command.toArray())
                .map(o -> o.toString())
                .forEachOrdered(commandJson::add);
        String foxxCommand = "{\"cmd\": " + commandJson.toString() + "}";
        System.out.println(foxxCommand);

        this.restTemplate.postForObject(ClusterConstant.WORKER_SERVICE_CLASS_CAPTURE_URL_CMD, foxxCommand, String.class);

    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {
        System.out.println("EVENT");
        System.out.println(command);
        //API
        if(command.size() > 1) {
            JsonArray eventJson = new JsonArray();
            Arrays.stream(command.toArray())
                    .map(o -> o.toString())
                    .forEachOrdered(eventJson::add);
            String foxxEvent = "{\"event\": " + eventJson.toString() + "}";
            System.out.println(foxxEvent);

            this.restTemplate.postForObject(ClusterConstant.WORKER_SERVICE_CLASS_CAPTURE_URL_EVENT, foxxEvent, String.class);
        }
    }
}
