package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.standard.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.standard.manager.ConnectorH2StandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.properties.ConnectorH2Properties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_STANDARD;

@Component(value = "standardWorker")
@ConditionalOnBean(ConnectorH2Properties.class)
public class ConnectorH2StandardWorker extends RunnableWorkerZeroMQ {

    private ConnectorH2StandardManager connectorH2StandardManager;
    private ConnectorH2Properties connectorH2Properties;
    private MessageCommand messageCommand;

    public ConnectorH2StandardWorker(ConnectorH2Properties connectorH2Properties, ConnectorH2StandardManager connectorH2StandardManager) {
        super();
        this.connectorH2StandardManager = connectorH2StandardManager;
        this.connectorH2Properties = connectorH2Properties;
        this.initRunnable(WORKER_SERVICE_CLASS_STANDARD, this.connectorH2Properties.getConnectorCommandBackEndReceiveConnection(), this.connectorH2Properties.getConnectorEventBackEndReceiveConnection(), null);
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        System.out.println("WORKER");
        Constant.Result result = null;
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand().toString()) {
            case "LIST":
                this.connectorH2StandardManager.list(this.messageCommand.getPayload());
                break;
            case "SELECT":
                this.connectorH2StandardManager.select(this.messageCommand.getPayload());
                break;
            case "INSERT":
                this.connectorH2StandardManager.insert(this.messageCommand.getPayload());
                break;
            case "UPDATE":
                this.connectorH2StandardManager.update(this.messageCommand.getPayload());
                break;
            case "DELETE":
                this.connectorH2StandardManager.delete(this.messageCommand.getPayload());
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
