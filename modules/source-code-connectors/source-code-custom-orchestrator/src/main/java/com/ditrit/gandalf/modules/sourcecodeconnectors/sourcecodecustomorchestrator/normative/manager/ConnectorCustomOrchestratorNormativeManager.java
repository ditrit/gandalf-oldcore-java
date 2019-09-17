package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.normative.manager;

import com.ditrit.gandalf.modules.abstractconnectors.abstractorchestrator.manager.ConnectorOrchestratorNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.core.ConnectorCustomOrchestratorBashService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.properties.ConnectorCustomOrchestratorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component(value = "normativeManager")
@ConditionalOnBean(ConnectorCustomOrchestratorProperties.class)
public class ConnectorCustomOrchestratorNormativeManager extends ConnectorOrchestratorNormativeManager {

    private ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService;
    private Gson mapper;
    @Value("${${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.uri}")
    private String uri;
    private RestTemplate restTemplate;

    @Autowired
    public ConnectorCustomOrchestratorNormativeManager(ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService) {
        this.connectorCustomOrchestratorBashService = connectorCustomOrchestratorBashService;
        this.restTemplate = new RestTemplate();
        this.mapper = new Gson();
    }

    @Override
    public boolean register(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        //return this.orchestratorServiceFeign.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/register/" + jsonObject.get("service").getAsString() + "/" + jsonObject.get("version").getAsString(), boolean.class);
    }

    @Override
    public boolean unregister(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        //return this.orchestratorServiceFeign.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/unregister/" + jsonObject.get("service").getAsString() + "/" + jsonObject.get("version").getAsString(), boolean.class);

    }

    @Override
    public boolean deploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "deploy");
        //return this.orchestratorServiceFeign.deploy(jsonObject.get("service").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/deploy/" + jsonObject.get("service").getAsString(), boolean.class);

    }

    @Override
    public boolean undeploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "undeploy");
        //return this.orchestratorServiceFeign.undeploy(jsonObject.get("service").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/undeploy/" + jsonObject.get("service").getAsString(), boolean.class);

    }

    @Override
    public boolean start(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "start");
        //return this.orchestratorServiceFeign.start(jsonObject.get("service").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/start/" + jsonObject.get("service").getAsString(), boolean.class);

    }

    @Override
    public boolean stop(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "stop");
       //return this.orchestratorServiceFeign.stop(jsonObject.get("service").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/stop/" + jsonObject.get("service").getAsString(), boolean.class);

    }

    @Override
    public boolean scaleUp(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_up");
        //return this.orchestratorServiceFeign.scaleUp(jsonObject.get("service").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/scale_up/" + jsonObject.get("service").getAsString(), boolean.class);

    }

    @Override
    public boolean scaleDown(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_down");
        //return this.orchestratorServiceFeign.scaleDown(jsonObject.get("service").getAsString());
        return this.restTemplate.getForObject(uri + "/orchestrator-service/scale_down/" + jsonObject.get("service").getAsString(), boolean.class);

    }

    public boolean downloadProject(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);

        String url = uri + "/orchestrator-service/download/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);

        return this.restTemplate.postForObject(url, requestEntity, boolean.class);

    }
}
