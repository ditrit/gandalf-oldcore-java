package com.ditrit.gandalf.jobs.deployjob.job;

import com.ditrit.gandalf.library.gandalfcustomclient.GandalfCustomClient;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.jobs.deployjob.feign.DeployFeign;
import com.ditrit.gandalf.jobs.deployjob.properties.DeployJobProperties;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.library.gandalfcustomclient"})
public class DeployJob implements JobHandler {

    private ZeebeClient zeebe;
    private DeployFeign deployFeign;
    private JobWorker subscription;
    private GandalfCustomClient gandalfCustomClient;
    private DeployJobProperties deployJobProperties;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public DeployJob(ZeebeClient zeebe, DeployFeign deployFeign, GandalfCustomClient gandalfCustomClient, ThreadPoolTaskExecutor threadPoolTaskExecutor, DeployJobProperties deployJobProperties) {
        this.zeebe = zeebe;
        this.deployFeign = deployFeign;
        this.gandalfCustomClient = gandalfCustomClient;
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

        ZMsg resultCommand = this.gandalfCustomClient.getCustomClient().sendCommandSync("deploy", this.deployJobProperties.getConnectorEndPointName(), "WORKER_SERVICE_CLASS_NORMATIVE", "DEPLOY", "5", payload.toString());
 /*       ZMsg resultCommand = null;
        while(resultCommand == null) {
            System.out.println("NULL");
            resultCommand = this.gandalfClient.getCommandResultAsync();
        }*/
        System.out.println(resultCommand);
        succes &= resultCommand.getLast().toString().equals("SUCCESS") ? true : false;
        System.out.println("SUCCESS");
        System.out.println(succes);

        if(succes) {
            //Send job complete command
            this.gandalfCustomClient.getCustomClient().sendEvent("build", "DEPLOY", "5",projectName + " deploy : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            this.gandalfCustomClient.getCustomClient().sendEvent("build", "DEPLOY", "5",projectName + " deploy : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
        }
    }
}
