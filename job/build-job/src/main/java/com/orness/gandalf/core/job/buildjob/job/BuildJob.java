package com.orness.gandalf.core.job.buildjob.job;

import com.orness.gandalf.core.job.buildjob.bash.BashExecutor;
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
    @Value("${gandalf.database.topic}")
    private String topicDatabase;

    private ZeebeClient zeebe;
    private BashExecutor bashExecutor;
    private JobWorker subscription;
    private ZeroMQJavaClient zeroMQJavaClient;

    @Autowired
    public BuildJob(ZeebeClient zeebe, BashExecutor bashExecutor) {
        this.zeebe = zeebe;
        this.bashExecutor = bashExecutor;
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

        //CLONE
        succes &= bashExecutor.cloneProject(workflow_variables.get(KEY_VARIABLE_PROJECT_URL).toString());
        //MVN CLEAN INSTALL
        succes &= bashExecutor.buildProject(workflow_variables.get(KEY_VARIABLE_PROJECT_NAME).toString());
        //ADD WORKFLOW VARIABLE ADD REPERTORY

        if(succes) {
            //Send job complete command
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
            //SEND MESSAGE DATABASE SUCCES
        }
        else {
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }

    }
}
