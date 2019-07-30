package com.orness.gandalf.core.module.kafkamodule.common.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.kafkamodule.common.manager.KafkaCommonManager;
import com.orness.gandalf.core.module.kafkamodule.core.KafkaCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import static com.orness.gandalf.core.module.busmodule.constant.BusConstant.*;

public class KafkaCommonWorkerCommand extends RunnableWorkerZeroMQ {

    @Autowired
    private KafkaCommonManager kafkaCommonManager;
    private Gson mapper;

    public KafkaCommonWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public Object parse(String messageContent) {
        return mapper.fromJson(messageContent, KafkaCommand.class);
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
                //TODO ADD ARTIFACT COMMAND
                this.kafkaCommonManager.deleteTopic("");
                break;
            case COMMAND_SEND_MESSAGE:
                //TODO ADD ARTIFACT COMMAND
                this.kafkaCommonManager.sendMessage("","");
                break;
            case COMMAND_RECEIVE_MESSAGE:
                //TODO ADD ARTIFACT COMMAND
                this.kafkaCommonManager.receiveMessage();
                break;
            case COMMAND_SYNCHRONIZE_GANDALF:
                //TODO ADD ARTIFACT COMMAND
                this.kafkaCommonManager.synchronizeGandalf();
                break;
            case COMMAND_SYNCHRONIZE_BUS:
                //TODO ADD ARTIFACT COMMAND
                this.kafkaCommonManager.synchronizeBus();
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
