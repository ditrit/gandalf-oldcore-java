package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.zeebe.client.ZeebeClient;
import org.zeromq.ZMsg;

import java.time.Duration;
import java.util.HashMap;

public class FunctionHookMerge extends Function {

    private Gson mapper;
    private ZeebeClient zeebe;

    public FunctionHookMerge() {
        super();
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        //TODO
        System.out.println("HOOK MANAGER");
        JsonObject jsonObject = this.mapper.fromJson(messageEvent.getPayload(), JsonObject.class);
        System.out.println(jsonObject.get("project_url").getAsString());
        HashMap<String, String> variables = new HashMap<>();
        variables.put("project_name", jsonObject.get("project_name").getAsString());
        variables.put("project_url", jsonObject.get("project_url").getAsString());
        //variables.put(" project_version", this.jsonObject.get("project_version").getAsString());
        System.out.println("VARIRABLES");
        System.out.println(variables.toString());
        System.out.println(messageEvent.getTopic());
        zeebe.newPublishMessageCommand() //
                .messageName("message")
                .correlationKey(messageEvent.getTopic())
                .variables(variables)
                .timeToLive(Duration.ofMinutes(100L))
                .send().join();
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {
        //TODO
        System.out.println("HOOK MANAGER");
        JsonObject jsonObject = this.mapper.fromJson(messageEvent.getPayload(), JsonObject.class);
        System.out.println(jsonObject.get("project_url").getAsString());
        HashMap<String, String> variables = new HashMap<>();
        variables.put("project_name", jsonObject.get("project_name").getAsString());
        variables.put("project_url", jsonObject.get("project_url").getAsString());
        //variables.put(" project_version", this.jsonObject.get("project_version").getAsString());
        System.out.println("VARIRABLES");
        System.out.println(variables.toString());
        System.out.println(messageEvent.getTopic());
        zeebe.newPublishMessageCommand() //
                .messageName("message")
                .correlationKey(messageEvent.getTopic())
                .variables(variables)
                .timeToLive(Duration.ofMinutes(100L))
                .send().join();
    }
}
