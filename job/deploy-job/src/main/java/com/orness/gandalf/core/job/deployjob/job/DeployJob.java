package com.orness.gandalf.core.job.deployjob.job;

import com.orness.gandalf.core.job.deployjob.feign.DeployFeign;
import com.orness.gandalf.core.library.zeromqjavaclient.ZeroMQJavaClient;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_PROJECT_NAME;

@Component
public class DeployJob implements JobHandler {

    @Value("${gandalf.communication.client}")
    private String connectionWorker;
    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;
    @Value("${gandalf.feign.topic}")
    private String topicBuild;

    private ZeebeClient zeebe;
    private DeployFeign deployFeign;
    private JobWorker subscription;
    private ZeroMQJavaClient zeroMQJavaClient;

    @Autowired
    public DeployJob(ZeebeClient zeebe, DeployFeign deployFeign) {
        this.zeebe = zeebe;
        this.deployFeign = deployFeign;
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
        zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);
        boolean succes = true;
        String projectName = workflow_variables.get(KEY_VARIABLE_PROJECT_NAME).toString();

        //DEPLOY
        succes &= deployFeign.deploy(projectName);

        if(succes) {
            //Send job complete command
            zeroMQJavaClient.sendMessageTopicDatabase(projectName + "build : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            zeroMQJavaClient.sendMessageTopicDatabase(projectName + "build : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
        }
    }
}
