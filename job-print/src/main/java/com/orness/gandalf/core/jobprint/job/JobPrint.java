package com.orness.gandalf.core.jobprint.job;


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
public class JobPrint implements JobHandler {

    @Autowired
    private ZeebeClient zeebe;

    private JobWorker subscription;

    @PostConstruct
    public void subscribe() {
        subscription = zeebe.newWorker()
                .jobType("print")
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

        //Variables
        Map<String, Object> workflow_variables = activatedJob.getVariablesAsMap();

        //Print
        System.out.println("PRINT");
        System.out.println("PRINT PROCESS ID " + workflow_variables.get("process_id"));
        System.out.println("PRINT NAME " + workflow_variables.get("name"));
        System.out.println("PRINT SEND TOPIC " + workflow_variables.get("listen_topic"));
        System.out.println("PRINT LISTEN TOPIC " + workflow_variables.get("send_topic"));
        System.out.println("PRINT CONTENT " + workflow_variables.get("content"));

        jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
    }
}
