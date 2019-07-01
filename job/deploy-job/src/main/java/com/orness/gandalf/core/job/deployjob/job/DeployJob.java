package com.orness.gandalf.core.job.deployjob.job;

import com.orness.gandalf.core.job.deployjob.archive.ArchiveService;
import com.orness.gandalf.core.job.deployjob.bash.BashService;
import com.orness.gandalf.core.job.deployjob.artifact.ArtifactService;
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

    private ZeebeClient zeebe;
    private BashService bashService;
    private ArchiveService archiveService;
    private ArtifactService artifactService;
    private JobWorker subscription;
    private ZeroMQJavaClient zeroMQJavaClient;

    @Autowired
    public DeployJob(ZeebeClient zeebe, BashService bashService, ArchiveService archiveService, ArtifactService artifactService) {
        this.zeebe = zeebe;
        this.bashService = bashService;
        this.archiveService = archiveService;
        this.artifactService = artifactService;
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
        //GET BUILD
        succes &= artifactService.getBuildFromStorage(projectName);
        //UNZIP
        succes &= archiveService.unzipBuildArchive(projectName);
        //DEPLOY
        //succes &= bashService.deployProject(projectName, 0);

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
