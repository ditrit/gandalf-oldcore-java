package com.orness.gandalf.core.job.registerjob.job;

import com.orness.gandalf.core.job.registerjob.feign.RegisterFeign;
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

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_PROJECT_NAME;

@Component
public class RegisterJob implements JobHandler {

    @Value("${gandalf.communication.client}")
    private String connectionWorker;
    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;
    @Value("${gandalf.register.topic}")
    private String topicRegister;


    private ZeebeClient zeebe;
    private RegisterFeign registerFeign;
    private JobWorker subscription;
    private ZeroMQJavaClient zeroMQJavaClient;

    @Autowired
    public RegisterJob(ZeebeClient zeebe, RegisterFeign registerFeign) {
        this.zeebe = zeebe;
        this.registerFeign = registerFeign;
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
        zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);
        boolean succes = true;
        //MessageGandalf message = zeroMQJavaClient.getMessageSubscriberCallableBusTopic(topicRegister);
        String projectName = workflow_variables.get(KEY_VARIABLE_PROJECT_NAME).toString();
        System.out.println(projectName);
        //Register
        //TODO VAR
        succes = registerFeign.register(projectName, "0");

        //ADD WORKFLOW VARIABLE ADD REPERTORY

        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("feign")
                .timeToLive(Duration.ofMinutes(30))
                .send().join();

        if(succes) {
            //Send job complete command
            zeroMQJavaClient.sendMessageTopicDatabase(projectName + "feign : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            zeroMQJavaClient.sendMessageTopicDatabase(projectName + "feign : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }

    }
}