package com.ditrit.gandalf.jobs.registerjob.job;

import com.ditrit.gandalf.jobs.registerjob.feign.RegisterFeign;
import com.ditrit.gandalf.jobs.registerjob.service.RegisterJobService;
import com.ditrit.gandalf.library.gandalfclient.GandalfClient;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.jobs.registerjob.properties.RegisterJobProperties;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;


@Component
@ComponentScan(basePackages = {"com.ditrit.gandalf.library.gandalfclient"})
public class RegisterJob implements JobHandler {

    private ZeebeClient zeebe;
    private RegisterFeign registerFeign;
    private JobWorker subscription;
    private GandalfClient gandalfClient;
    private RegisterJobProperties registerJobProperties;
    private RegisterJobService registerJobService;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public RegisterJob(ZeebeClient zeebe, RegisterFeign registerFeign, GandalfClient gandalfClient, ThreadPoolTaskExecutor threadPoolTaskExecutor, RegisterJobProperties registerJobProperties, RegisterJobService registerJobService) {
        this.zeebe = zeebe;
        this.registerFeign = registerFeign;
        this.gandalfClient = gandalfClient;
        this.registerJobProperties = registerJobProperties;
        this.registerJobService = registerJobService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }


    @PostConstruct
    public void subscribe() {
        subscription = zeebe.newWorker()
                .jobType("job_register")
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

        Map<String, Object> workflow_variables = activatedJob.getVariablesAsMap();
        System.out.println(workflow_variables);
        boolean succes = true;

        String projectName = workflow_variables.get("project_name").toString();
        String projectVersion = workflow_variables.get("project_version").toString();

        JsonObject payloadRegister = new JsonObject();
        payloadRegister.addProperty("service", projectName);
        payloadRegister.addProperty("version", projectVersion);

        ZMsg resultCommand = this.registerJobService.sendCommand("REGISTER", payloadRegister);

        succes &= resultCommand.getLast().toString().equals("SUCCESS") ? true : false;


        if(succes) {
            this.registerJobService.sendEvent("register", "REGISTER", projectName + " register : success");
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            this.registerJobService.sendEvent("register", "REGISTER", projectName + " register : fail");
            jobClient.newFailCommand(activatedJob.getKey());
        }
    }
}