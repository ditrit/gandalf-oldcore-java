package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.normative.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.normative.manager.ConnectorZeebeNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.event.domain.MessageEvent;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorZeebeNormativeManager connectorZeebeNormativeManager;
    private ConnectorZeebeProperties connectorZeebeProperties;
    private MessageCommand messageCommand;
    private MessageEvent messageEvent;

    @Autowired
    public ConnectorZeebeNormativeWorker(ConnectorZeebeProperties connectorZeebeProperties, ConnectorZeebeNormativeManager connectorZeebeNormativeManager) {
        super();
        this.connectorZeebeNormativeManager = connectorZeebeNormativeManager;
        this.connectorZeebeProperties = connectorZeebeProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorZeebeProperties.getConnectorCommandBackEndConnection(), this.connectorZeebeProperties.getConnectorEventBackEndConnection(), this.connectorZeebeProperties.getTopics());
    }
//TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        System.out.println("COMMAND");
        System.out.println(command);
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "DEPLOY":
                this.connectorZeebeNormativeManager.deployWorkflow(this.messageCommand.getPayload());
                break;
            case "INSTANCIATE":
                this.connectorZeebeNormativeManager.instanciateWorkflow(this.messageCommand.getPayload());
                break;
            case "SEND":
                this.connectorZeebeNormativeManager.sendMessage(this.messageCommand.getPayload());
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
        this.messageEvent = new MessageEvent(command);
        System.out.println(messageEvent.getEvent());
        switch(messageEvent.getEvent()) {
            case "HOOK_MERGE":
                this.connectorZeebeNormativeManager.hookMerge(this.messageEvent);
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
