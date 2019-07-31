package com.orness.gandalf.core.module.jobmodule.core;

import com.orness.gandalf.core.library.gandalfjavaclient.GandalfJavaClient;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import io.zeebe.util.collection.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.HashMap;
//TODO REVOIR GENERIC
public abstract class Job implements JobHandler {

    protected ZeebeClient zeebe;
    protected JobWorker subscription;
    protected GandalfJavaClient gandalfJavaClient;
    @Value("${gandalf.build.topic}")
    private String jobType;

    @Autowired
    public Job(ZeebeClient zeebe) {
        this.zeebe = zeebe;
    }

    @PostConstruct
    public void subscribe() {
        subscription = zeebe.newWorker()
                .jobType(jobType)
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
        Tuple<Boolean, HashMap<String, String>> results = job(jobClient, activatedJob);

        if(results.getLeft()) {
            //Send job complete command
            //TODO SEND DATAABSE ???
            //gandalfJavaClient.sendEvent(projectUrl + "build : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(results.getRight()).send().join();
        }
        else {
            //zeroMQJavaClient.sendMessageTopicDatabase(projectUrl + "build : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }
    }

    public abstract Tuple<Boolean, HashMap<String, String>> job(JobClient jobClient, ActivatedJob activatedJob);
}
