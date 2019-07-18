package com.orness.gandalf.core.service.workflowservice.controller;

import com.orness.gandalf.core.service.workflowservice.manager.WorkflowManager;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.*;

@RestController
public class WorkflowController {


    @Value("${gandalf.communication.client}")
    private String connectionWorker;

    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;

    private WorkflowManager workflowManager;
    private ZeebeClient zeebe;

    @Autowired
    public WorkflowController(WorkflowManager workflowManager, ZeebeClient zeebe) {
        this.workflowManager = workflowManager;
        this.zeebe = zeebe;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/workflow/deploy/{workflow}")
    public boolean deploy(@PathVariable("workflow") String workflow) {
        DeploymentEvent deploymentEvent = workflowManager.deployWorkflow(workflow);
        String id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/workflow/instance/{workflow}")
    public boolean instanciate(@PathVariable("workflow") String workflow) {
        DeploymentEvent deploymentEvent = workflowManager.deployWorkflow(workflow);
        String id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);

        //INSTANCE VARIABLES
        HashMap<String, String> workflow_variables = new HashMap();
        workflow_variables.put(KEY_VARIABLE_WORKFLOW_ID, id);
        workflow_variables.put(KEY_VARIABLE_WORKFLOW_NAME, workflow);
        workflow_variables.put(KEY_VARIABLE_WORKFLOW_TOPIC, "webhook");
        workflow_variables.put(KEY_VARIABLE_WORKFLOW_MESSAGE, "");
        workflow_variables.put(KEY_VARIABLE_PROJECT_URL, "");
        workflow_variables.put(KEY_VARIABLE_PROJECT_NAME, "");
        workflow_variables.put(KEY_VARIABLE_PROJECT_VERSION, "");

        WorkflowInstanceEvent workflowInstanceEvent = workflowManager.InstanceWorkflow(workflow_variables);
        return true;
    }
}
