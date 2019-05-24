package com.orness.gandalf.core.jobkafkaproducer.job;

import com.orness.gandalf.core.grpcjavaclient.bus.GrpcBusJavaClient;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;

@Component
public class JobKafkaProducer implements JobHandler {

    private ZeebeClient zeebe;
    private JobWorker subscription;

    @Autowired
    public JobKafkaProducer(ZeebeClient zeebe) {
        this.zeebe = zeebe;
    }

    @PostConstruct
    public void subscribe() {
        subscription = zeebe.newWorker()
                .jobType("kafka_producer")
                .handler(this)
                .timeout(Duration.ofMinutes(20))
                .open();
    }

    @PreDestroy
    public void closeSubscription() {
        subscription.close();
    }

    @Override
    public void handle(JobClient jobClient, ActivatedJob activatedJob) {

        //Get workflow variables
        Map<String, Object> workflow_variables = activatedJob.getVariablesAsMap();

        //Send message to ConnectorBus
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.createTopic(workflow_variables.get("send_topic").toString());
        System.out.println("SEND " + workflow_variables.get("send_topic").toString());
        grpcBusJavaClient.sendMessage(workflow_variables.get("send_topic").toString(), workflow_variables.get("name").toString(), workflow_variables.get("content").toString());

        //Send job complete command
        jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
    }
}
