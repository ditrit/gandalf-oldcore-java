package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

public class FunctionStart extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;

    public FunctionStart() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "start");
        //return this.orchestratorServiceFeign.start(jsonObject.get("service").getAsString());
        this.restTemplate.getForObject(uri + "/orchestrator-service/start/" + jsonObject.get("service").getAsString(), boolean.class);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
