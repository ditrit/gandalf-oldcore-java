package com.ditrit.gandalf.gandalfjava.jobs.deployjob.job;

import com.ditrit.gandalf.gandalfjava.library.gandalfclient.GandalfClient;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.gandalfjava.jobs.deployjob.feign.DeployFeign;
import com.ditrit.gandalf.gandalfjava.jobs.deployjob.properties.DeployJobProperties;
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
public class DeployJob implements JobHandler {

    private ZeebeClient zeebe;
    private DeployFeign deployFeign;
    private JobWorker subscription;
    private GandalfClient gandalfClient;
    private DeployJobProperties deployJobProperties;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public DeployJob(ZeebeClient zeebe, DeployFeign deployFeign, GandalfClient gandalfClient, ThreadPoolTaskExecutor threadPoolTaskExecutor, DeployJobProperties deployJobProperties) {
        this.zeebe = zeebe;
        this.deployFeign = deployFeign;
        this.gandalfClient = gandalfClient;
        this.deployJobProperties = deployJobProperties;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @PostConstruct
    public void subscribe() {
        subscription = zeebe.newWorker()
                .jobType("job_deploy")
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
        boolean succes = true;
        String projectName = workflow_variables.get("project_name").toString();
        String version = workflow_variables.get("project_version").toString();

        //ORCHESTRATOR
        JsonObject payload = new JsonObject();
        payload.addProperty("service", projectName);
        payload.addProperty("version", version);

        ZMsg resultCommand = this.gandalfClient.getClientCommand().sendCommandSync("deploy", this.deployJobProperties.getConnectorEndPointName(), "WORKER_SERVICE_CLASS_STANDARD", "DEPLOY", "5", payload.toString());

        System.out.println(resultCommand);
        succes &= resultCommand.getLast().toString().equals("SUCCESS") ? true : false;
        System.out.println("SUCCESS");
        System.out.println(succes);

        if(succes) {
            //Send job complete command
            this.gandalfClient.getClientEvent().sendEvent("build", "DEPLOY", "5",projectName + " deploy : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            this.gandalfClient.getClientEvent().sendEvent("build", "DEPLOY", "5",projectName + " deploy : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
        }
    }
}
