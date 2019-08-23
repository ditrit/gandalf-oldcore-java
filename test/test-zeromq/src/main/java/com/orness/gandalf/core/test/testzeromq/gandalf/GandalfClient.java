package com.orness.gandalf.core.test.testzeromq.gandalf;

import com.orness.gandalf.core.test.testzeromq.command.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_COMMAND_SERVICE_GANDALF;
import static com.orness.gandalf.core.test.testzeromq.gandalf.GandalfConstant.COMMAND_START;

@Component
public class GandalfClient extends Client {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfClient(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        String[] connections = new String[3];
        connections[0] = gandalfProperties.getClientBackEndConnection1();
        connections[1] = gandalfProperties.getClientBackEndConnection2();
        connections[2] = gandalfProperties.getClientBackEndConnection3();
        //TODO APPLICATION NAME
        this.init(connections, "client0");
    }


    public void sendCommand(String uuid, String service, String command, String payload) {
        this.backEndClient.sendMore(uuid);
        this.backEndClient.sendMore(this.identity);
        this.backEndClient.sendMore(service);
        this.backEndClient.sendMore(command);
        this.backEndClient.send(payload);
    }
}
