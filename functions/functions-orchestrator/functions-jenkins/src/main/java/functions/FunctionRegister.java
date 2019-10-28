package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

public class FunctionRegister extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;

    public FunctionRegister() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String uri = jsonObject.get("").toString();
        //return this.connectorCustomOrchestratorBashService.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        //return this.orchestratorServiceFeign.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        System.out.println("URL");
        System.out.println(uri + "/orchestrator-service/register/" + jsonObject.get("service").getAsString() + "/" + jsonObject.get("version").getAsString());
        this.restTemplate.getForObject(uri + "/orchestrator-service/register/" + jsonObject.get("service").getAsString() + "/" + jsonObject.get("version").getAsString(), boolean.class);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
