package com.orness.gandalf.core.module.h2module.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.h2module.normative.manager.ConnectorH2NormativeManager;
import com.orness.gandalf.core.module.h2module.properties.ConnectorH2Properties;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@Profile(value = "h2")
public class ConnectorH2NormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorH2NormativeManager h2CommonManager;
    private ConnectorH2Properties connectorH2Properties;
    private MessageCommand messageCommand;

    public ConnectorH2NormativeWorker(ConnectorH2Properties connectorH2Properties, ConnectorH2NormativeManager h2CommonManager) {
        super();
        this.h2CommonManager = h2CommonManager;
        this.connectorH2Properties = connectorH2Properties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorH2Properties.getConnectorCommandBackEndConnection(), this.connectorH2Properties.getConnectorEventBackEndConnection(), this.connectorH2Properties.getTopics());
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        System.out.println("WORKER");
        Constant.Result result = null;
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand().toString()) {
            case "LIST":
                this.h2CommonManager.list(this.messageCommand.getPayload());
                break;
            case "SELECT":
                this.h2CommonManager.select(this.messageCommand.getPayload());
                break;
            case "INSERT":
                this.h2CommonManager.insert(this.messageCommand.getPayload());
                break;
            case "UPDATE":
                this.h2CommonManager.update(this.messageCommand.getPayload());
                break;
            case "DELETE":
                this.h2CommonManager.delete(this.messageCommand.getPayload());
                break;
                //TODO DELETE
            case "TEST":
                System.out.println("WOKER TEST");
                result = Constant.Result.SUCCESS;
                break;
            default:
                //DO NOTHING
                break;
        }
        return result;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
