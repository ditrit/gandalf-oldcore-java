package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zeromq.ZMsg;

public class FunctionSynchronizeTopicKafka extends Function {

    public static final String COMMAND = "SYNCHRONIZE_TOPIC_KAFKA";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;

    public FunctionSynchronizeTopicKafka() {
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        if(this.getSynchronizeTopics().contains(topic)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("event", event);
            jsonObject.addProperty("payload", payload);
            System.out.println("SEND TO KAFKA");
            System.out.println(topic);
            System.out.println(jsonObject.toString());
            this.connectorKafkaProducer.sendKafka(topic, jsonObject.toString());
        }
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
