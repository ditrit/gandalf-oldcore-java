package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import org.zeromq.ZMsg;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FunctionDeployWorkflow extends Function {

    private Gson mapper;
    private ZeebeClient zeebe;

    public FunctionDeployWorkflow() {
        super();
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        //TODO
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        DeploymentEvent deploymentEvent = zeebe.newDeployCommand()
                .addResourceFromClasspath(jsonObject.get("workflow").getAsString())
                .send().join();
        this.getIdDeployment(deploymentEvent);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
