package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.normative.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.normative.manager.ConnectorKafkaNormativeManager;
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
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorKafkaNormativeManager  connectorKafkaNormativeManager;
    private ConnectorKafkaProperties connectorKafkaProperties;
    private MessageCommand messageCommand;
    private MessageEvent messageEvent;

    @Autowired
    public ConnectorKafkaNormativeWorker(ConnectorKafkaProperties connectorKafkaProperties, ConnectorKafkaNormativeManager connectorKafkaNormativeManager) {
        super();
        this.connectorKafkaProperties = connectorKafkaProperties;
        this.connectorKafkaNormativeManager = connectorKafkaNormativeManager;
        this.connectorKafkaNormativeManager.setSynchronizeTopics(connectorKafkaProperties.getSynchronizeTopics());
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorKafkaProperties.getConnectorCommandBackEndConnection(), this.connectorKafkaProperties.getConnectorEventBackEndReceiveConnection(), null);
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "CREATE_TOPIC":
                this.connectorKafkaNormativeManager.createTopic(this.messageCommand.getPayload());
                break;
            case "DELETE_TOPIC":
                this.connectorKafkaNormativeManager.deleteTopic(this.messageCommand.getPayload());
                break;
            case "SEND_MESSAGE":
                this.connectorKafkaNormativeManager.sendMessage(this.messageCommand.getPayload());
                break;
            case "RECEIVE_MESSAGE":
                this.connectorKafkaNormativeManager.receiveMessage(this.messageCommand.getPayload());
                break;
            case "SYNCHRONIZE_GANDALF":
                this.connectorKafkaNormativeManager.addSynchronizeTopicToGandalf(this.messageCommand.getPayload());
                break;
            case "SYNCHRONIZE_BUS":
                this.connectorKafkaNormativeManager.addSynchronizeTopicToBus(this.messageCommand.getPayload());
                break;
            default:
                //DO NOTHING
                break;
        }
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg event) {
        this.messageEvent = new MessageEvent(event);
        //Synchronize
        this.connectorKafkaNormativeManager.synchronizeToBus(messageEvent.getTopic(), messageEvent.getEvent(), messageEvent.getPayload());

        switch(messageEvent.getEvent()) {
            default:
                //DO NOTHING
                break;
        }
    }
}
