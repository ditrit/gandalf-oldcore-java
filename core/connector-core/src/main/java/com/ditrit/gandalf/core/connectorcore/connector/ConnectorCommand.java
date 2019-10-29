package com.ditrit.gandalf.core.connectorcore.connector;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.core.zeromqcore.command.connector.RunnableConnectorCommandZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

@Component(value = "connectorCommand")
@Scope("singleton")
public class ConnectorCommand extends RunnableConnectorCommandZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorCommand(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorCommandFrontEndSendConnection(), this.connectorProperties.getConnectorCommandFrontEndReceiveConnection(), this.connectorProperties.getConnectorCommandBackEndSendConnection(), this.connectorProperties.getConnectorCommandBackEndReceiveConnection());
        this.getConfiguration();
    }

    private void getConfiguration() {
        ZMsg req_configuration = new ZMsg();
        req_configuration.add("CONFIGURATION");
        req_configuration.add(this.connectorProperties.getConnectorName());
        req_configuration.add(this.connectorProperties.getConnectorType());
        req_configuration.add(this.connectorProperties.getInstanceName());
        req_configuration.send(this.frontEndSendRoutingConnector);
        req_configuration.destroy();

        ZMsg rep_configuration = null;

        while(rep_configuration == null) {
            rep_configuration = ZMsg.recvMsg(this.frontEndSendRoutingConnector, ZMQ.NOBLOCK);
            boolean more = this.frontEndSendRoutingConnector.hasReceiveMore();

            if(more) {
                break;
            }
        }

        Object[] configurationArray = rep_configuration.toArray();
        rep_configuration.destroy();

        // PUB SEND
        this.connectorProperties.setConnectorEventFrontEndSendConnection(configurationArray[5].toString());
        // PUB RECEIVE
        this.connectorProperties.setConnectorEventFrontEndReceiveConnection(configurationArray[6].toString());
        //TODO
        //REQ AGGREGATOR
        // NOM
        // AGGREGATOR
        // UUID
        // IP
        // MAC
        // TYPE C
        // TYPE P
    }
}
