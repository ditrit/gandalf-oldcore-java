package com.ditrit.gandalf.jobs.buildjob.job;

import com.ditrit.gandalf.jobs.buildjob.feign.BuildFeign;
import com.ditrit.gandalf.jobs.buildjob.manager.BuildJobManager;
import com.ditrit.gandalf.jobs.buildjob.properties.BuildJobProperties;
import com.ditrit.gandalf.library.gandalfclient.GandalfClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;


@Component
@ComponentScan(basePackages = {"com.ditrit.gandalf.library.gandalfclient"})
public class BuildJob implements JobHandler {

    private ZeebeClient zeebe;
    private BuildFeign buildFeign;
    private JobWorker subscription;
    private GandalfClient gandalfClient;
    private BuildJobManager buildJobManager;
    private BuildJobProperties buildJobProperties;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private Gson mapper;


    @Autowired
    public BuildJob(ZeebeClient zeebe, BuildFeign buildFeign, BuildJobManager buildJobManager, GandalfClient gandalfClient, ThreadPoolTaskExecutor threadPoolTaskExecutor, BuildJobProperties buildJobProperties) {
        this.zeebe = zeebe;
        this.buildFeign = buildFeign;
        this.buildJobManager = buildJobManager;
        this.gandalfClient = gandalfClient;
        this.buildJobProperties = buildJobProperties;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.mapper = new Gson();

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

        Map<String, Object> current_workflow_variables = activatedJob.getVariablesAsMap();
        String projectUrl = current_workflow_variables.get("project_url").toString();
        String projectName = current_workflow_variables.get("project_name").toString();

        JsonObject request = new JsonObject();
        request.addProperty("name", projectName);
        request.addProperty("url", projectUrl);

        String result = buildFeign.build(request.toString());
        JsonObject response = this.mapper.fromJson(result, JsonObject.class);
        succes &= response.get("succes").getAsBoolean();

        current_workflow_variables.put("project_version", response.get("version").getAsString());
        current_workflow_variables.put("project_url", response.get("project_url").getAsString());
        current_workflow_variables.put("conf_url", response.get("conf_url").getAsString());

        zeebe.newPublishMessageCommand()
                .messageName("message")
                .correlationKey("build")
                .variables(current_workflow_variables)
                .timeToLive(Duration.ofMinutes(30))
                .send().join();

        if(succes) {
            gandalfClient.getClient().sendEvent("build", "BUILD", "5", projectUrl + " build : success" );
            jobClient.newCompleteCommand(activatedJob.getKey()).variables(current_workflow_variables).send().join();
        }
        else {
            gandalfClient.getClient().sendEvent("build", "BUILD", "5", projectUrl + " build : fail" );
            jobClient.newFailCommand(activatedJob.getKey());
        }

    }
}
