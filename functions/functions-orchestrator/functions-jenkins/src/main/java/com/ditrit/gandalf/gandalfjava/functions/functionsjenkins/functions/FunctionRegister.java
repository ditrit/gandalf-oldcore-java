package com.ditrit.gandalf.gandalfjava.functions.functionsjenkins.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;
import com.ditrit.gandalf.gandalfjava.functions.functionsjenkins.properties.ConnectorCustomOrchestratorProperties;

import java.util.List;

public class FunctionRegister extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;

    public FunctionRegister() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String uri = this.connectorCustomOrchestratorProperties.getTargetEndPointConnection();

        this.restTemplate.getForObject(uri + "/orchestrator-service/register/" + jsonObject.get("service").getAsString() + "/" + jsonObject.get("version").getAsString(), boolean.class);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
