package com.orness.core.workflowservice.domain;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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

    public WorkflowInstanceEvent InstanceWorkflow(String workflow_bpmn_process_id, String workflow_id, String workflow_name, String workflow_topic, String workflow_content) {
        //INSTANCE VARIABLES
        HashMap<String, String> workflow_variables = new HashMap();
        workflow_variables.put("workflow_process_id", workflow_bpmn_process_id);
        workflow_variables.put("workflow_id", workflow_id);
        workflow_variables.put("workflow_name", workflow_name);
        workflow_variables.put("workflow_topic", workflow_topic);
        workflow_variables.put("workflow_content", workflow_content);

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
