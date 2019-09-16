package com.orness.gandalf.core.job.buildjob.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class BuildJobManager {

    public Map<String, String> createWorkflowVariables(Map<String, Object> current_workflow_variables) {
        Map<String, String> workflow_variables = new HashMap<>();
/*        CustomMergeRequest customMergeRequest = null;
        for (Map.Entry<String,Object> entry : current_workflow_variables.entrySet()) {
            if(entry.getKey().equals("KEY_VARIABLE_WORKFLOW_MESSAGE")) {
                customMergeRequest = CustomMergeRequestParser.parseStringToObject(entry.getValue().toString());
            }
            workflow_variables.put(entry.getKey(), entry.getValue().toString());

        }
        if(customMergeRequest != null) {
            workflow_variables.put("project_url", customMergeRequest.getProjectHttpUrl());
            workflow_variables.put("project_name", customMergeRequest.getProjectName());
        }*/
        return workflow_variables;
    }

}
