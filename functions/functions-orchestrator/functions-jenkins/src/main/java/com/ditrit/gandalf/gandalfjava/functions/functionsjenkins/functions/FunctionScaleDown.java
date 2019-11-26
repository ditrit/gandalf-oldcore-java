package com.ditrit.gandalf.gandalfjava.functions.functionsjenkins.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;
import com.ditrit.gandalf.gandalfjava.functions.functionsjenkins.properties.ConnectorCustomOrchestratorProperties;

public class FunctionScaleDown extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;

    public FunctionScaleDown() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();

        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);

        String uri = this.connectorCustomOrchestratorProperties.getTargetEndPointConnection();
        this.restTemplate.getForObject(uri + "/orchestrator-service/scale_down/" + jsonObject.get("service").getAsString(), boolean.class);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
