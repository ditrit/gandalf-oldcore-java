package com.orness.gandalf.core.connector.connectorbusservice.specific.kafka.communication.command;

import com.google.gson.Gson;
import com.orness.gandalf.core.connector.connectorbusservice.gandalf.manager.ConnectorBusManager;
import com.orness.gandalf.core.connector.connectorbusservice.specific.kafka.producer.ConnectorBusProducer;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class BusWorkerZeroMQ extends RunnableWorkerZeroMQ {

    @Autowired
    private ConnectorBusManager connectorBusManager;

    @Autowired
    private ConnectorBusProducer connectorBusProducer;
    private Gson mapper = new Gson();

    public BusWorkerZeroMQ(@Value("${gandalf.communication.worker}") String connection) {
        super(connection);
    }


    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

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
}
