package functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.library.gandalfclient.GandalfClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zeromq.ZMsg;

public class FunctionHookMerge extends Function {

    private Gson mapper;
    private GandalfClient gandalfClient;

    public FunctionHookMerge() {
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String event = command.toArray()[4].toString();
        JsonObject jsonObject = this.mapper.fromJson(event, JsonObject.class);
        JsonObject jsonObjectProject = jsonObject.get("project").getAsJsonObject();
        JsonObject jsonObjectRepository = jsonObject.get("repository").getAsJsonObject();
        JsonObject jsonObjectAttributes = jsonObject.get("object_attributes").getAsJsonObject();

        JsonObject payload = new JsonObject();

        String topic = "demonstration";
        payload.addProperty("correlation_key", topic);
        payload.addProperty("project_url", jsonObjectProject.get("git_http_url").getAsString());
        payload.addProperty("project_name", jsonObjectProject.get("name").getAsString());

        this.gandalfClient.getClient().sendEvent(topic, "HOOK_MERGE", "5", payload.toString());
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
