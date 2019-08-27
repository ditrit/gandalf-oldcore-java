package com.orness.gandalf.core.module.kafkamodule.common.worker;

import com.orness.gandalf.core.module.kafkamodule.common.manager.KafkaCommonManager;
import com.orness.gandalf.core.module.kafkamodule.core.KafkaCommand;
import com.orness.gandalf.core.module.kafkamodule.core.properties.KafkaProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.busmodule.constant.BusConstant.*;

@Component(value = "commonWorkerCommand")
@Profile(value = "kafka-module")
public class KafkaCommonRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    private KafkaCommonManager kafkaCommonManager;
    private KafkaProperties kafkaProperties;

    @Autowired
    public KafkaCommonRoutingRoutingWorkerCommand(KafkaCommonManager kafkaCommonManager, KafkaProperties kafkaProperties) {
        super();
        this.kafkaCommonManager = kafkaCommonManager;
        this.kafkaProperties = kafkaProperties;
        this.connect(kafkaProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return this.mapper.fromJson(messageContent, KafkaCommand.class);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        //TODO REVOIR ARGS
        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_CREATE_TOPIC:
                this.kafkaCommonManager.createTopic("");
                break;
            case COMMAND_DELETE_TOPIC:
                this.kafkaCommonManager.deleteTopic("");
                break;
            case COMMAND_SEND_MESSAGE:
                this.kafkaCommonManager.sendMessage("","");
                break;
            case COMMAND_RECEIVE_MESSAGE:
                this.kafkaCommonManager.receiveMessage("");
                break;
            case COMMAND_SYNCHRONIZE_GANDALF:
                this.kafkaCommonManager.synchronizeToGandalf("");
                break;
            case COMMAND_SYNCHRONIZE_BUS:
                this.kafkaCommonManager.synchronizeToBus("");
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
