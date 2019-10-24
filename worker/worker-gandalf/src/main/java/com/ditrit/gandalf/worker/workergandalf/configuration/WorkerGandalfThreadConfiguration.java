package com.ditrit.gandalf.worker.workergandalf.configuration;

import com.ditrit.gandalf.core.clientcore.properties.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.zeromq.ZMsg;
import properties.WorkerProperties;
import service.WorkerClientService;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.workercore"})
@Order
public class WorkerGandalfThreadConfiguration {

    private ClientProperties clientProperties;
    private WorkerProperties workerProperties;
    private WorkerClientService workerClientService;

    @Autowired
    public WorkerGandalfThreadConfiguration(ClientProperties clientProperties, WorkerProperties workerProperties, WorkerClientService workerClientService) {
        this.clientProperties = clientProperties;
        this.workerProperties = workerProperties;
        this.workerClientService = workerClientService;
        this.initialize();
    }

    public ClientProperties getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    public WorkerProperties getWorkerProperties() {
        return workerProperties;
    }

    public void setWorkerProperties(WorkerProperties workerProperties) {
        this.workerProperties = workerProperties;
    }

    public WorkerClientService getWorkerClientService() {
        return workerClientService;
    }

    public void setWorkerClientService(WorkerClientService workerClientService) {
        this.workerClientService = workerClientService;
    }

    public void initialize() {
        ZMsg response = this.workerClientService.sendRequest("configuration");
        Object[] responseConnections =  response.toArray();

        this.workerProperties.setWorkerCommandFrontEndReceiveConnection(responseConnections[0].toString());
        this.workerProperties.setWorkerEventFrontEndReceiveConnection(responseConnections[1].toString());

        this.clientProperties.setConnectorCommandBackEndSendConnection(responseConnections[2].toString());
        this.clientProperties.setConnectorEventBackEndSendConnection(responseConnections[3].toString());

        //TODO TYPE / FUNCTION

        //TODO SEND COMMANDS CONNECTOR -> VERIF COMMANDS
    }
}
