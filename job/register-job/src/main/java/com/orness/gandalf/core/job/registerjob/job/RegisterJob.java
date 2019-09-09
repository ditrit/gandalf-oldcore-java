package com.orness.gandalf.core.job.registerjob.job;

import com.google.gson.JsonObject;
import com.orness.gandalf.core.job.registerjob.feign.RegisterFeign;
import com.orness.gandalf.core.job.registerjob.properties.RegisterJobProperties;
import com.orness.gandalf.core.module.clientcore.GandalfClient;
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
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.clientcore"})
public class RegisterJob implements JobHandler {

    private ZeebeClient zeebe;
    private RegisterFeign registerFeign;
    private JobWorker subscription;
    private GandalfClient gandalfClient;
    private RegisterJobProperties registerJobProperties;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public RegisterJob(ZeebeClient zeebe, RegisterFeign registerFeign, GandalfClient gandalfClient, ThreadPoolTaskExecutor threadPoolTaskExecutor, RegisterJobProperties registerJobProperties) {
        this.zeebe = zeebe;
        this.registerFeign = registerFeign;
        this.gandalfClient = gandalfClient;
        this.registerJobProperties = registerJobProperties;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.threadPoolTaskExecutor.execute(gandalfClient.getClientCommand());
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

        //Get workflow variables
        Map<String, Object> workflow_variables = activatedJob.getVariablesAsMap();
        System.out.println(workflow_variables);
        boolean succes = true;
        //MessageGandalf message = zeroMQJavaClient.getMessageSubscriberCallableBusTopic(topicRegister);
        String projectName = workflow_variables.get("project_name").toString();
        String projectVersion = workflow_variables.get("project_version").toString();
        String projectUrl = workflow_variables.get("project_url").toString();
        String confUrl = workflow_variables.get("conf_url").toString();
        System.out.println(projectName);
        System.out.println(projectVersion);

/*        //Register
        succes = registerFeign.register(projectName, projectVersion);

        //ADD WORKFLOW VARIABLE ADD REPERTORY
        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("feign")
                .timeToLive(Duration.ofMinutes(30))
                .send().join();*/

        //ORCHESTRATOR
        //SEND DOWNLOAD
        JsonObject payloadDownload = new JsonObject();
        payloadDownload.addProperty("project_url", projectUrl);
        payloadDownload.addProperty("conf_url", confUrl);

        this.gandalfClient.sendCommand("download", this.registerJobProperties.getConnectorEndPointName(), "WORKER_SERVICE_CLASS_NORMATIVE", "DOWNLOAD", payloadDownload.toString());
      /*  ZMsg resultCommand = null;
        while(resultCommand == null) {
            resultCommand = this.gandalfClient.getCommandResult();
        }
        System.out.println(resultCommand);
        succes &= resultCommand.getLast().toString().equals("SUCCES") ? true : false;
        System.out.println("SUCCES");
        System.out.println(succes);*/

        //SEND REGISTER
        JsonObject payloadRegister = new JsonObject();
        payloadRegister.addProperty("service", projectName);
        payloadRegister.addProperty("version", projectVersion);

        this.gandalfClient.sendCommand("register", this.registerJobProperties.getConnectorEndPointName(), "WORKER_SERVICE_CLASS_NORMATIVE", "REGISTER", payloadRegister.toString());
        /*while(resultCommand == null) {
            resultCommand = this.gandalfClient.getCommandResult();
        }
        System.out.println(resultCommand);
        succes &= resultCommand.getLast().toString().equals("SUCCES") ? true : false;
        System.out.println("SUCCES");
        System.out.println(succes);*/

        if(succes) {
            //Send job complete command
            this.gandalfClient.sendEvent("build", "REGISTER", projectName + "feign : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            this.gandalfClient.sendEvent("build", "REGISTER", projectName + "feign : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }

    }
}