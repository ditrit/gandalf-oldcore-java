package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;
import properties.ConnectorCustomOrchestratorProperties;

public class FunctionScaleUp extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;

    @Autowired
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;

    public FunctionScaleUp() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);

        this.restTemplate.getForObject(this.connectorCustomOrchestratorProperties.getTargetEndPointConnection() + "/orchestrator-service/scale_up/" + jsonObject.get("service").getAsString(), boolean.class);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
