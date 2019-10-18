package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zeromq.ZMsg;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeleteTopic extends Function {

    public static final String COMMAND = "DELETE_TOPIC";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;

    public FunctionDeleteTopic() {

    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String topic = jsonObject.get("topic").getAsString();

        AdminClient adminClient = AdminClient.create(this.kafkaAdmin.getConfig());
        if(!this.isTopicExist(topic, adminClient)) {
            List<String> deleteTopics = new ArrayList<>();
            deleteTopics.add(topic);
            adminClient.deleteTopics(deleteTopics);
        }
        adminClient.close();
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
