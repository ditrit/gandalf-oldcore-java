package com.ditrit.gandalf.gandalfjava.functions.functionszeebe.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionDeployWorkflow extends CommandFunction {

    private Gson mapper;

    @Autowired
    private ZeebeClient zeebe;

    public FunctionDeployWorkflow() {
        super();
        this.mapper = new Gson();
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        DeploymentEvent deploymentEvent = zeebe.newDeployCommand()
                .addResourceFromClasspath(jsonObject.get("workflow").getAsString())
                .send().join();
        this.getIdDeployment(deploymentEvent);
        return null;
    }

    private String getIdDeployment(DeploymentEvent deploymentEvent) {
        return deploymentEvent.getWorkflows().get(0).getBpmnProcessId();
    }
}
