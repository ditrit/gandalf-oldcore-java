package com.orness.gandalf.core.job.buildjob.job;

import com.orness.gandalf.core.job.buildjob.archive.ArchiveService;
import com.orness.gandalf.core.job.buildjob.bash.BashService;
import com.orness.gandalf.core.job.buildjob.storage.StorageService;
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
    private BashService bashService;
    private ArchiveService archiveService;
    private StorageService storageService;
    private JobWorker subscription;
    private ZeroMQJavaClient zeroMQJavaClient;

    @Autowired
    public BuildJob(ZeebeClient zeebe, BashService bashService, ArchiveService archiveService, StorageService storageService) {
        this.zeebe = zeebe;
        this.bashService = bashService;
        this.archiveService = archiveService;
        this.storageService = storageService;
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
        //MessageGandalf message = zeroMQJavaClient.getMessageSubscriberCallableBusTopic(topicWebhook);
        String projectName = workflow_variables.get(KEY_VARIABLE_PROJECT_URL).toString();
        //CLONE
        succes &= bashService.cloneProject(projectName);
        //MVN CLEAN INSTALL
        succes &= bashService.buildProject(projectName);
        //TAR
        //TODO PATH
        succes &= archiveService.zipArchive(projectName);
        //SEND TO STORAGE
        succes &= storageService.sendBuildToStorage(projectName);
        //ADD WORKFLOW VARIABLE ADD REPERTORY

        if(succes) {
            //Send job complete command
            zeroMQJavaClient.sendMessageTopicDatabase(projectName + "build : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
        }
        else {
            jobClient.newFailCommand(activatedJob.getKey());
            //SEND MESSAGE DATABASE FAIL
        }

    }
}
