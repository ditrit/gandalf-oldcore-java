package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zeromq.ZMsg;

public class FunctionSendMessage extends Function {

    public static final String COMMAND = "SEND_MESSAGE";
    private Gson mapper;

    public FunctionSendMessage() {

    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorKafkaProducer.sendKafka(jsonObject.get("topic").getAsString(), jsonObject.get("message").getAsString());
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
