package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import org.springframework.kafka.core.KafkaAdmin;
import org.zeromq.ZMsg;

public class FunctionAddSynchronizeTopicGandalf extends Function {

    public static final String COMMAND = "ADD_SYNCHRONIZE_TOPIC_GANDALF";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;

    public FunctionAddSynchronizeTopicGandalf() {
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
