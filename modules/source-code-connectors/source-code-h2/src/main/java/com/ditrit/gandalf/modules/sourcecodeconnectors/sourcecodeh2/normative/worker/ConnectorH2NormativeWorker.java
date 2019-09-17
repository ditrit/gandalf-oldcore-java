package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.normative.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.normative.manager.ConnectorH2NormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.properties.ConnectorH2Properties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@ConditionalOnBean(ConnectorH2Properties.class)
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
