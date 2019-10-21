package com.ditrit.gandalf.tests.testzeromq.gandalf;

import com.ditrit.gandalf.tests.testzeromq.command.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfClient extends Client {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfClient(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        String[] connections = new String[3];
        connections[0] = this.gandalfProperties.getClientBackEndConnection1();
        connections[1] = this.gandalfProperties.getClientBackEndConnection2();
        connections[2] = this.gandalfProperties.getClientBackEndConnection3();
        this.init(this.gandalfProperties.getConnectorName(), connections);
    }


    public void sendCommand(String uuid, String connector, String serviceClass, String command, String payload) {
        this.backEndClient.sendMore(uuid);
        this.backEndClient.sendMore(this.identity);
        this.backEndClient.sendMore(connector);
        this.backEndClient.sendMore(serviceClass);
        this.backEndClient.sendMore(command);
        this.backEndClient.send(payload);
    }
}
