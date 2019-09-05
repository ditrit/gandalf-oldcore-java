package com.orness.gandalf.core.job.deployjob.job;

import com.orness.gandalf.core.job.deployjob.feign.DeployFeign;
import com.orness.gandalf.core.module.clientcore.GandalfClient;
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

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_PROJECT_NAME;

@Component
public class DeployJob implements JobHandler {

    private ZeebeClient zeebe;
    private DeployFeign deployFeign;
    private JobWorker subscription;
    private GandalfClient gandalfClient;

    @Autowired
    public DeployJob(ZeebeClient zeebe, DeployFeign deployFeign, GandalfClient gandalfClient) {
        this.zeebe = zeebe;
        this.deployFeign = deployFeign;
        this.gandalfClient = gandalfClient;
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
        String projectName = workflow_variables.get(KEY_VARIABLE_PROJECT_NAME).toString();

        //DEPLOY
        succes &= deployFeign.deploy(projectName);

        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("deploy")
                .timeToLive(Duration.ofMinutes(30))
                .send().join();

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
