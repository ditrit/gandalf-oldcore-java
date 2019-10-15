package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.standard.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.standard.manager.ConnectorZeebeStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.event.domain.MessageEvent;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
//TODO REMOVE
import org.springframework.web.client.RestTemplate;
//TODO END REMOVE
import org.zeromq.ZMsg;

import java.util.Arrays;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_STANDARD;

@Component(value = "standardWorker")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeStandardWorker extends RunnableWorkerZeroMQ {

    private ConnectorZeebeStandardManager connectorZeebeStandardManager;
    private ConnectorZeebeProperties connectorZeebeProperties;
    private MessageCommand messageCommand;
    private MessageEvent messageEvent;

    @Autowired
    public ConnectorZeebeStandardWorker(ConnectorZeebeProperties connectorZeebeProperties, ConnectorZeebeStandardManager connectorZeebeStandardManager) {
        super();
        this.connectorZeebeStandardManager = connectorZeebeStandardManager;
        this.connectorZeebeProperties = connectorZeebeProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_STANDARD, this.connectorZeebeProperties.getConnectorCommandBackEndReceiveConnection(), this.connectorZeebeProperties.getConnectorEventBackEndReceiveConnection(), null);
    }
//TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        System.out.println("COMMAND");
        System.out.println(command);
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "DEPLOY":
                this.connectorZeebeStandardManager.deployWorkflow(this.messageCommand.getPayload());
                break;
            case "INSTANCIATE":
                this.connectorZeebeStandardManager.instanciateWorkflow(this.messageCommand.getPayload());
                break;
            case "SEND":
                this.connectorZeebeStandardManager.sendMessage(this.messageCommand.getPayload());
                break;
            default:
                //DO NOTHING
                break;
        }
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {
        System.out.println("EVENT SUBS");
        System.out.println(command);
        //TODO REMOVE
        String foxxCommand = "{cmd :" + Arrays.toString(command.toArray()) + "}";
        new RestTemplate().postForObject( "http://arangodb.service.gandalf/_db/gandalf/keep/event", foxxCommand, String.class);
        //TODO END REMOVE
        this.messageEvent = new MessageEvent(command);
        System.out.println(messageEvent.getEvent());
        switch(messageEvent.getEvent()) {
            case "HOOK_MERGE":
                this.connectorZeebeStandardManager.hookMerge(this.messageEvent);
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
