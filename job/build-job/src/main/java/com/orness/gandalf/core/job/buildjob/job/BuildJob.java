package com.orness.gandalf.core.job.buildjob.job;

import com.orness.gandalf.core.job.buildjob.feign.BuildFeign;
import com.orness.gandalf.core.job.buildjob.manager.BuildJobManager;
import com.orness.gandalf.core.module.clientcore.GandalfClient;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.*;

@Component
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.clientcore"})
public class BuildJob implements JobHandler {

    private ZeebeClient zeebe;
    private BuildFeign buildFeign;
    private JobWorker subscription;
    private GandalfClient gandalfClient;
    private BuildJobManager buildJobManager;

    @Autowired
    public BuildJob(ZeebeClient zeebe, BuildFeign buildFeign, BuildJobManager buildJobManager, GandalfClient gandalfClient) {
        this.zeebe = zeebe;
        this.buildFeign = buildFeign;
        this.buildJobManager = buildJobManager;
        this.gandalfClient = gandalfClient;
    }

    @PostConstruct
    public void subscribe() {
        subscription = zeebe.newWorker()
                .jobType("job_build")
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
        boolean succes = true;

        //Get workflow variables
        Map<String, Object> current_workflow_variables = activatedJob.getVariablesAsMap();
        Map<String, String> workflow_variables = buildJobManager.createWorkflowVariables(current_workflow_variables);
        String projectUrl = workflow_variables.get(KEY_VARIABLE_PROJECT_URL);
        String projectName = workflow_variables.get(KEY_VARIABLE_PROJECT_NAME);

        //Build
        String projectVersion = buildFeign.build(projectName, projectUrl);
        System.out.println(projectUrl);
        System.out.println(projectVersion);
        succes &= projectVersion != null;

        workflow_variables.put(KEY_VARIABLE_PROJECT_VERSION, projectVersion);

        //ADD WORKFLOW VARIABLE ADD REPERTORY
        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("build")
                .variables(workflow_variables)
                .timeToLive(Duration.ofMinutes(30))
                .send().join();

        if(succes) {
            //Send job complete command
            gandalfClient.sendEvent("build", "BUILD", projectUrl + "build : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            gandalfClient.sendEvent("build", "BUILD", projectUrl + "build : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }

    }
}
