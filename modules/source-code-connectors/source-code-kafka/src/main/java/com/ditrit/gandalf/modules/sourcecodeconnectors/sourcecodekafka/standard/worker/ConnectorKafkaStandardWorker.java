package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.standard.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.standard.manager.ConnectorKafkaStandardManager;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.event.domain.MessageEvent;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;


import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_STANDARD;

@Component(value = "standardWorker")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaStandardWorker extends RunnableWorkerZeroMQ {

    private ConnectorKafkaStandardManager connectorKafkaStandardManager;
    private ConnectorKafkaProperties connectorKafkaProperties;
    private MessageCommand messageCommand;
    private MessageEvent messageEvent;

    @Autowired
    public ConnectorKafkaStandardWorker(ConnectorKafkaProperties connectorKafkaProperties, ConnectorKafkaStandardManager connectorKafkaStandardManager) {
        super();
        this.connectorKafkaProperties = connectorKafkaProperties;
        this.connectorKafkaStandardManager = connectorKafkaStandardManager;
        this.connectorKafkaStandardManager.setSynchronizeTopics(connectorKafkaProperties.getSynchronizeTopics());
        this.initRunnable(WORKER_SERVICE_CLASS_STANDARD, this.connectorKafkaProperties.getConnectorCommandBackEndReceiveConnection(), this.connectorKafkaProperties.getConnectorEventBackEndReceiveConnection(), null);
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "CREATE_TOPIC":
                this.connectorKafkaStandardManager.createTopic(this.messageCommand.getPayload());
                break;
            case "DELETE_TOPIC":
                this.connectorKafkaStandardManager.deleteTopic(this.messageCommand.getPayload());
                break;
            case "SEND_MESSAGE":
                this.connectorKafkaStandardManager.sendMessage(this.messageCommand.getPayload());
                break;
            case "RECEIVE_MESSAGE":
                this.connectorKafkaStandardManager.receiveMessage(this.messageCommand.getPayload());
                break;
            case "SYNCHRONIZE_GANDALF":
                this.connectorKafkaStandardManager.addSynchronizeTopicToGandalf(this.messageCommand.getPayload());
                break;
            case "SYNCHRONIZE_BUS":
                this.connectorKafkaStandardManager.addSynchronizeTopicToBus(this.messageCommand.getPayload());
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
        this.connectorKafkaStandardManager.synchronizeToBus(messageEvent.getTopic(), messageEvent.getEvent(), messageEvent.getPayload());

        switch(messageEvent.getEvent()) {
            default:
                //DO NOTHING
                break;
        }
    }
}
