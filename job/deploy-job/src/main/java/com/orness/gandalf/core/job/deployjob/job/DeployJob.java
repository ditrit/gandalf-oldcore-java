package com.orness.gandalf.core.job.deployjob.job;

import com.google.gson.JsonObject;
import com.orness.gandalf.core.job.deployjob.feign.DeployFeign;
import com.orness.gandalf.core.job.deployjob.properties.DeployJobProperties;
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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;


@Component
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.clientcore"})
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
        this.threadPoolTaskExecutor.execute(gandalfClient.getClientCommand());
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
        String version = workflow_variables.get("version").toString();

/*        //DEPLOY
        succes &= deployFeign.deploy(projectName);

        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("deploy")
                .timeToLive(Duration.ofMinutes(30))
                .send().join();*/

        //ORCHESTRATOR
        JsonObject payload = new JsonObject();
        payload.addProperty("service", projectName);
        payload.addProperty("version", version);

        this.gandalfClient.sendCommand("deploy", this.deployJobProperties.getConnectorEndPointName(), "WORKER_SERVICE_CLASS_NORMATIVE", "DEPLOY", payload.getAsString());
        //TODO RESULT

        if(succes) {
            //Send job complete command
            this.gandalfClient.sendEvent("build", "DEPLOY",projectName + " deploy : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            this.gandalfClient.sendEvent("build", "DEPLOY",projectName + " deploy : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
        }
    }
}
