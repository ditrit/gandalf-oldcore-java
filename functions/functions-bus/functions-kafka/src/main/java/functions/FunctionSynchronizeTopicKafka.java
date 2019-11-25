package functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.producer.FunctionKafkaProducer;
import org.springframework.kafka.core.KafkaAdmin;
import org.zeromq.ZMsg;

public class FunctionSynchronizeTopicKafka extends Function {

    public static final String COMMAND = "SYNCHRONIZE_TOPIC_KAFKA";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;
    private FunctionKafkaProducer functionKafkaProducer;

    public FunctionSynchronizeTopicKafka() {
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        Object[] commandArray = command.toArray();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", commandArray[5].toString());
        jsonObject.addProperty("payload", commandArray[6].toString());

        this.functionKafkaProducer.sendKafka(commandArray[3].toString(), jsonObject.toString());

        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
