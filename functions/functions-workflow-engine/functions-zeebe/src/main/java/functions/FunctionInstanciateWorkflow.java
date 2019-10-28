package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.zeebe.client.ZeebeClient;
import org.zeromq.ZMsg;

public class FunctionInstanciateWorkflow extends Function {

    private Gson mapper;
    private ZeebeClient zeebe;

    public FunctionInstanciateWorkflow() {
        super();
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = "";
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        zeebe.newCreateInstanceCommand()
                .bpmnProcessId(jsonObject.get("id").getAsString())
                .latestVersion()
                .variables(jsonObject.get("variables").getAsString())
                .send().join();
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
