package com.orness.gandalf.core.module.h2module.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.h2module.normative.manager.ConnectorH2NormativeManager;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "commonWorkerCommand")
@Profile(value = "h2-module")
public class ConnectorH2NormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorH2NormativeManager h2CommonManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;

    public ConnectorH2NormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorH2NormativeManager h2CommonManager) {
        super();
        this.h2CommonManager = h2CommonManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        /*//TODO REVOIR ARGS
        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_LIST:
                this.h2CommonManager.list(null);
                break;
            case COMMAND_SELECT:
                this.h2CommonManager.select(null ,0L);
                break;
            case COMMAND_INSERT:
                this.h2CommonManager.insert(null,"");
                break;
            case COMMAND_UPDATE:
                this.h2CommonManager.update(null, 0L, "");
                break;
            case COMMAND_DELETE:
                this.h2CommonManager.delete(null, 0L);
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
