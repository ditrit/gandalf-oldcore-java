package com.orness.gandalf.core.service.workflowservice.manager;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_PROJECT_URL;

@Component
public class WorkflowManager {

    ZeebeClient zeebeClient;

    @Autowired
    public WorkflowManager(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public DeploymentEvent deployWorkflow(String workflow_name) {
        DeploymentEvent deploymentEvent = zeebeClient.newDeployCommand()
                .addResourceFromClasspath(workflow_name)
                .send().join();

        return deploymentEvent;
    }

    public WorkflowInstanceEvent InstanceWorkflow(String workflow_bpmn_process_id, String workflow_name, String workflow_content, String workflow_listen_topic, String workflow_send_topic) {
        //INSTANCE VARIABLES
        HashMap<String, String> workflow_variables = new HashMap();
        workflow_variables.put("process_id", workflow_bpmn_process_id);
        workflow_variables.put("name", workflow_name);
        workflow_variables.put("content", workflow_content);
        workflow_variables.put("listen_topic", workflow_listen_topic);
        workflow_variables.put("send_topic", workflow_send_topic);
        workflow_variables.put("feign", "feign");
        workflow_variables.put(KEY_VARIABLE_PROJECT_URL, "git@gitlab:romain.fairant/test.git");

        System.out.println(workflow_bpmn_process_id);
        //INSTANCE
        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(workflow_bpmn_process_id)
                .latestVersion()
                .variables(workflow_variables)
                .send().join();
    }

    public String getIdDeployment(DeploymentEvent deploymentEvent) {
        return deploymentEvent.getWorkflows().get(0).getBpmnProcessId();
    }

}
