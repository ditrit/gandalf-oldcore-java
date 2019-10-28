package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

public class FunctionUnregister extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;

    public FunctionUnregister() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        //return this.orchestratorServiceFeign.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        this.restTemplate.getForObject(uri + "/orchestrator-service/unregister/" + jsonObject.get("service").getAsString() + "/" + jsonObject.get("version").getAsString(), boolean.class);

        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
