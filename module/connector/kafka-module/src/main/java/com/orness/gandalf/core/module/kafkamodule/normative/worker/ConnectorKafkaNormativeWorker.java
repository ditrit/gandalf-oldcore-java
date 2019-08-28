package com.orness.gandalf.core.module.kafkamodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.kafkamodule.normative.manager.ConnectorKafkaNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "commonWorkerCommand")
@Profile(value = "kafka-module")
public class ConnectorKafkaNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorKafkaNormativeManager  connectorKafkaNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;

    @Autowired
    public ConnectorKafkaNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorKafkaNormativeManager connectorKafkaNormativeManager) {
        super();
        this.connectorKafkaNormativeManager = connectorKafkaNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
/*        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_CREATE_TOPIC:
                this.kafkaCommonManager.createTopic("");
                break;
            case COMMAND_DELETE_TOPIC:
                this.kafkaCommonManager.deleteTopic("");
                break;
            case COMMAND_SEND_MESSAGE:
                this.kafkaCommonManager.sendMessage("","");
                break;
            case COMMAND_RECEIVE_MESSAGE:
                this.kafkaCommonManager.receiveMessage("");
                break;
            case COMMAND_SYNCHRONIZE_GANDALF:
                this.kafkaCommonManager.synchronizeToGandalf("");
                break;
            case COMMAND_SYNCHRONIZE_BUS:
                this.kafkaCommonManager.synchronizeToBus("");
                break;
            default:
                //DO NOTHING
                break;
        }*/
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
