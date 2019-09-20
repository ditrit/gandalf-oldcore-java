package com.orness.gandalf.core.module.gandalfmodule.worker;

import com.orness.gandalf.core.module.gandalfmodule.manager.ConnectorGandalfManager;
import com.orness.gandalf.core.module.gandalfmodule.properties.ConnectorGandalfProperties;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import java.util.List;

import static com.orness.gandalf.core.module.gandalfmodule.constant.ConnectorGandalfConstant.WORKER_SERVICE_CLASS_ADMIN;

@Component(value = "gandalfWorker")
public class ConnectorGandalfWorker extends RunnableWorkerZeroMQ {

    private ConnectorGandalfProperties connectorGandalfProperties;
    private ConnectorGandalfManager connectorGandalfManager;

    @Autowired
    public ConnectorGandalfWorker(ConnectorGandalfProperties connectorGandalfProperties, ConnectorGandalfManager connectorGandalfManager) {
        this.connectorGandalfProperties = connectorGandalfProperties;
        this.connectorGandalfManager = connectorGandalfManager;
        this.initRunnable(WORKER_SERVICE_CLASS_ADMIN, this.connectorGandalfProperties.getConnectorCommandBackEndConnection(), this.connectorGandalfProperties.getConnectorEventBackEndConnection(), null);
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
