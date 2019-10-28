package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import core.domain.ConnectorZeebeMessage;
import io.zeebe.client.ZeebeClient;
import org.zeromq.ZMsg;

import java.time.Duration;

public class FunctionSendMessage extends Function {

    private Gson mapper;
    private ZeebeClient zeebe;

    public FunctionSendMessage() {
        super();
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        //TODO REVOIR
        String payload = "";
        ConnectorZeebeMessage connectorZeebeMessage = this.mapper.fromJson(payload, ConnectorZeebeMessage.class);
        zeebe.newPublishMessageCommand() //
                .messageName(connectorZeebeMessage.getName())
                .correlationKey(connectorZeebeMessage.getCorrelationKey())
                .variables(connectorZeebeMessage.getVariables())
                .timeToLive(Duration.ofMinutes(connectorZeebeMessage.getDuration()))
                .send().join();
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
