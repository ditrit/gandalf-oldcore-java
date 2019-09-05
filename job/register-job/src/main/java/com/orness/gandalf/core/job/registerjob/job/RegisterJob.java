package com.orness.gandalf.core.job.registerjob.job;

import com.orness.gandalf.core.job.registerjob.feign.RegisterFeign;
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

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_PROJECT_NAME;
import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_PROJECT_VERSION;

@Component
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.clientcore"})
public class RegisterJob implements JobHandler {

    private ZeebeClient zeebe;
    private RegisterFeign registerFeign;
    private JobWorker subscription;
    private GandalfClient gandalfClient;

    @Autowired
    public RegisterJob(ZeebeClient zeebe, RegisterFeign registerFeign, GandalfClient gandalfClient) {
        this.zeebe = zeebe;
        this.registerFeign = registerFeign;
        this.gandalfClient = gandalfClient;
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
        String projectName = workflow_variables.get(KEY_VARIABLE_PROJECT_NAME).toString();
        String projectVersion = workflow_variables.get(KEY_VARIABLE_PROJECT_VERSION).toString();
        System.out.println(projectName);
        System.out.println(projectVersion);

        //Register
        succes = registerFeign.register(projectName, projectVersion);

        //ADD WORKFLOW VARIABLE ADD REPERTORY
        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("feign")
                .timeToLive(Duration.ofMinutes(30))
                .send().join();

        if(succes) {
            //Send job complete command
            this.gandalfClient .sendEvent("build", "REGISTER", projectName + "feign : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            this.gandalfClient .sendEvent("build", "REGISTER", projectName + "feign : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }

    }
}