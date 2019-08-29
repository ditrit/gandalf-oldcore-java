package com.orness.gandalf.core.module.kafkamodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.kafkamodule.normative.manager.ConnectorKafkaNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@Profile(value = "kafka-module")
public class ConnectorKafkaNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorKafkaNormativeManager  connectorKafkaNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorKafkaNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorKafkaNormativeManager connectorKafkaNormativeManager) {
        super();
        this.connectorKafkaNormativeManager = connectorKafkaNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand().toString()) {
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
                this.connectorKafkaNormativeManager.synchronizeToGandalf(this.messageCommand.getPayload());
                break;
            case "SYNCHRONIZE_BUS":
                this.connectorKafkaNormativeManager.synchronizeToBus(this.messageCommand.getPayload());
                break;
            default:
                //DO NOTHING
                break;
        }
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
