package com.orness.gandalf.core.job.deployjob.job;

import com.orness.gandalf.core.job.deployjob.archive.ArchiveService;
import com.orness.gandalf.core.job.deployjob.bash.BashService;
import com.orness.gandalf.core.job.deployjob.storage.StorageService;
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
    @Value("${gandalf.deploy.topic}")
    private String topicBuild;
    @Value("${gandalf.deploy.topic}")
    private String topicDatabase;

    private ZeebeClient zeebe;
    private BashService bashService;
    private ArchiveService archiveService;
    private StorageService storageService;
    private JobWorker subscription;
    private ZeroMQJavaClient zeroMQJavaClient;

    @Autowired
    public DeployJob(ZeebeClient zeebe, BashService bashService, ArchiveService archiveService, StorageService storageService) {
        this.zeebe = zeebe;
        this.bashService = bashService;
        this.archiveService = archiveService;
        this.storageService = storageService;
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

        //GET BUILD
        succes &= storageService.getBuildFromStorage();
        //UNZIP
        succes &= archiveService.unzipBuildArchive();
        //DEPLOY
        succes &= bashService.deployProject(workflow_variables.get(KEY_VARIABLE_PROJECT_NAME).toString(), 0);

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
