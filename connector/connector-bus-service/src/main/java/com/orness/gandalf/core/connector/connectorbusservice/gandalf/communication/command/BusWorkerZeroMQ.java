package com.orness.gandalf.core.connector.connectorbusservice.gandalf.communication.command;

import com.google.gson.Gson;
import com.orness.gandalf.core.connector.connectorbusservice.gandalf.manager.ConnectorBusManager;
import com.orness.gandalf.core.connector.connectorbusservice.specific.kafka.producer.ConnectorBusProducer;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

@Component
@Scope("singleton")
public class BusWorkerZeroMQ extends WorkerZeroMQ implements Runnable {

    @Autowired
    private ConnectorBusManager connectorBusManager;

    @Autowired
    private ConnectorBusProducer connectorBusProducer;

    private Gson mapper = new Gson();

    public BusWorkerZeroMQ(@Value("${gandalf.communication.worker}") String connection) {
        super(connection);
    }

    public void command(ZFrame sender, String command, String content) {

        System.out.println("ID " + this.identity);
        System.out.println("REQ ID " + sender);
        System.out.println("REQ COMMAND " + command);
        System.out.println("REQ CONTENT " + content);

        switch(command) {
            case COMMAND_SEND_MESSAGE_TOPIC:
                String[] contents = content.split("#");
                String topic = contents[0];
                String message = contents[1];
                MessageGandalf messageGandalf = new MessageGandalf(topic, sender.toString(), "2020-12-09 01:02:03.123456789", "2020-12-09", message);
                //mapper.fromJson(contents[1], MessageGandalf.class);
                connectorBusProducer.sendConnectorMessageKafka(topic, messageGandalf);
                break;
            case COMMAND_DELETE_TOPIC:
                connectorBusManager.deleteTopic(content);
                break;
            case COMMAND_CREATE_TOPIC:
                connectorBusManager.createTopic(content);
                break;
            default:
                //DO NOTHING
                break;
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ZMsg msg = ZMsg.recvMsg(this.worker);
            ZFrame sender = msg.pop();
            ZFrame command = msg.pop();
            ZFrame content = msg.pop();

            this.command(sender, command.toString(), content.toString());

            //  Send reply back to client
            sender.send(worker, ZFrame.REUSE + ZFrame.MORE);
            command.send(worker, ZFrame.REUSE + ZFrame.MORE);
            content.send(worker, ZFrame.REUSE);
            //worker.send("World".getBytes(), 0);
        }
    }
}
