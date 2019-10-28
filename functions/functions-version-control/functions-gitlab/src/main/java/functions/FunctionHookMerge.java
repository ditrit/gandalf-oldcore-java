package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zeromq.ZMsg;

public class FunctionHookMerge extends Function {

    private Gson mapper;

    public FunctionHookMerge() {
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        //TODO
        JsonObject jsonObject = this.mapper.fromJson(hook, JsonObject.class);
        JsonObject jsonObjectProject = jsonObject.get("project").getAsJsonObject();
        JsonObject jsonObjectRepository = jsonObject.get("repository").getAsJsonObject();
        JsonObject jsonObjectAttributes = jsonObject.get("object_attributes").getAsJsonObject();

        JsonObject payload = new JsonObject();
        //StringBuilder topic = new StringBuilder(jsonObjectRepository.get("name").getAsString()).append(".").append(jsonObjectProject.get("name").getAsString());
        String topic = "demonstration";
        payload.addProperty("correlation_key", topic);
        payload.addProperty("project_url", jsonObjectProject.get("git_http_url").getAsString());
        payload.addProperty("project_name", jsonObjectProject.get("name").getAsString());
        System.out.println("SEND");
        System.out.println(topic.toString());
        System.out.println("HOOK_MERGE");
        System.out.println(payload.toString());
        //this.gandalfClient.sendCommand("toto", "toto", "toto", "toto", "toto");
        this.gandalfClient.getClient().sendEvent(topic, "HOOK_MERGE", "5", payload.toString());
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
