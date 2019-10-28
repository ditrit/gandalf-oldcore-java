package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;
import properties.ConnectorCustomOrchestratorProperties;

public class FunctionDeploy extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;

    public FunctionDeploy() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String uri = this.connectorCustomOrchestratorProperties.getTargetEndPointConnection();
        this.restTemplate.getForObject(uri + "/orchestrator-service/deploy/" + jsonObject.get("service").getAsString(), boolean.class);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
