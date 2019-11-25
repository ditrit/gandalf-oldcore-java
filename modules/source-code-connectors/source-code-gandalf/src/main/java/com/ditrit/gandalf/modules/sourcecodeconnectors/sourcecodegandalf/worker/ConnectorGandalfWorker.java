package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.manager.ConnectorGandalfManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.properties.ConnectorGandalfProperties;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.constant.ConnectorGandalfConstant.WORKER_SERVICE_CLASS_ADMIN;

@Component(value = "gandalfWorker")
public class ConnectorGandalfWorker extends RunnableWorkerZeroMQ {

    private ConnectorGandalfProperties connectorGandalfProperties;
    private ConnectorGandalfManager connectorGandalfManager;

    @Autowired
    public ConnectorGandalfWorker(ConnectorGandalfProperties connectorGandalfProperties, ConnectorGandalfManager connectorGandalfManager) {
        this.connectorGandalfProperties = connectorGandalfProperties;
        this.connectorGandalfManager = connectorGandalfManager;
        this.initRunnable(WORKER_SERVICE_CLASS_ADMIN, this.connectorGandalfProperties.getConnectorCommandBackEndReceiveConnection(), this.connectorGandalfProperties.getConnectorEventBackEndReceiveConnection(), null);
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        //TODO
        //this.connectorGandalfManager.start();
        return Constant.Result.FAIL;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {
        //TODO
    }


}
