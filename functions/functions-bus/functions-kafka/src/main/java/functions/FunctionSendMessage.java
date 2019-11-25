package functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.producer.FunctionKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.ZMsg;

public class FunctionSendMessage extends Function {

    public static final String COMMAND = "SEND_MESSAGE";
    private Gson mapper;

    @Autowired
    private FunctionKafkaProducer functionKafkaProducer;

    public FunctionSendMessage() {

    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.functionKafkaProducer.sendKafka(jsonObject.get("topic").getAsString(), jsonObject.get("message").getAsString());
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
