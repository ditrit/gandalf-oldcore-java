package com.orness.core.jobtestprint.job;


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
import java.util.HashMap;
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
                .timeout(Duration.ofMinutes(10))
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
        System.out.println(workflow_variables);
        System.out.println("PRINT PROCESS ID " + workflow_variables.get("workflow_process_id"));
        System.out.println("PRINT ID " + workflow_variables.get("workflow_id"));
        System.out.println("PRINT NAME " + workflow_variables.get("workflow_name"));
        System.out.println("PRINT TOPIC " + workflow_variables.get("workflow_topic"));
        System.out.println("PRINT CONTENT " + workflow_variables.get("workflow_content"));

        jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
    }
}
