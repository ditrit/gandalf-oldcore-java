package com.ditrit.gandalf.gandalfjava.core.clientcore.client;

import com.ditrit.gandalf.gandalfjava.core.clientcore.properties.ClientProperties;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.library.client.RunnableClientZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "clientCommand")
@Scope("singleton")
public class ClientCommand extends RunnableClientZeroMQ {

    private ClientProperties clientProperties;

    @Autowired
    public ClientCommand(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.initRunnable(this.clientProperties.getConnectorName(), this.clientProperties.getConnectorCommandBackEndSendConnection());
    }

    public ZMsg sendCommandSync(String context, String timeout, String uuid, String command, String payload) {
        return this.sendCommandSync(context, timeout, uuid, command, payload);
    }

    public void sendCommandAsync(String context, String timeout, String uuid, String command, String payload) {
        if(!Thread.currentThread().isInterrupted()) {
            this.run();
        }
        this.sendCommandAsync(context, timeout, uuid, command, payload);
    }

    public ZMsg getCommandResultAsync() {
        if(!Thread.currentThread().isInterrupted()) {
            this.run();
        }
        return this.getCommandResultAsync();
    }

    public void sendFileConnector() {
        //SEND COMMAND
        //RECEIVE REP_COMMAND (FQDN + TOKEN)
        //SEND + START TRANSACTION BY CLUSTER
        //SEND + END TRANSACTION BY CLUSTER
        //END
    }

    public void sendFileCluster() {
        //SEND COMMAND
        //RECEIVE REP_COMMAND (FQDN + TOKEN)
        //START TRANSACTION BY CONNECTOR
        //END
    }
}
