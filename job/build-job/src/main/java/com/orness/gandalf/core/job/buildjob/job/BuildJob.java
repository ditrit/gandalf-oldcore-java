package com.orness.gandalf.core.job.buildjob.job;

import com.orness.gandalf.core.job.buildjob.feign.BuildFeign;
import com.orness.gandalf.core.library.zeromqjavaclient.ZeroMQJavaClient;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
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

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.*;

@Component
public class BuildJob implements JobHandler {

    @Value("${gandalf.communication.client}")
    private String connectionWorker;
    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;
    @Value("${gandalf.build.topic}")
    private String topicWebhook;


    private ZeebeClient zeebe;
    private BuildFeign buildFeign;
    private JobWorker subscription;
    private ZeroMQJavaClient zeroMQJavaClient;

    @Autowired
    public BuildJob(ZeebeClient zeebe, BuildFeign buildFeign) {
        this.zeebe = zeebe;
        this.buildFeign = buildFeign;
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

        //Get workflow variables
        Map<String, Object> workflow_variables = activatedJob.getVariablesAsMap();
        zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);
        boolean succes = true;
        MessageGandalf message = zeroMQJavaClient.getMessageSubscriberCallableBusTopic(topicWebhook);
        String projectUrl = workflow_variables.get(KEY_VARIABLE_PROJECT_URL).toString();
        //Build
        succes = buildFeign.build(projectUrl);

        //ADD WORKFLOW VARIABLE ADD REPERTORY

        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("build")
                .timeToLive(Duration.ofMinutes(30))
                .send().join();

        if(succes) {
            //Send job complete command
            zeroMQJavaClient.sendMessageTopicDatabase(projectUrl + "build : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            zeroMQJavaClient.sendMessageTopicDatabase(projectUrl + "build : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }

    }
}
